package website.lhc.heron.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import website.lhc.heron.commo.enums.JobStatusEnum;
import website.lhc.heron.dto.TaskDto;
import website.lhc.heron.framework.quartz.QuartzManager;
import website.lhc.heron.mapper.TaskMapper;
import website.lhc.heron.model.Task;
import website.lhc.heron.service.TaskService;
import website.lhc.heron.util.Assert;
import website.lhc.heron.util.JsonUtil;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: 582895699@qq.com
 * @time: 2020/9/28 下午 10:53
 */
@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private QuartzManager quartzManager;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addTask(TaskDto dto) {
        Task task = new Task();
        task.setBeanClass(dto.getBeanClass());
        task.setCreateTime(new Date());
        task.setCreateUser("admin");
        task.setCronExpression(dto.getCronExpression());
        task.setDescription(dto.getDescription());
        task.setJobGroup(dto.getJobGroup());
        task.setJobName(dto.getTaskName());
        task.setJobStatus(dto.getJobStatus());
        taskMapper.insertSelective(task);
        try {
            quartzManager.addTask(task);
        } catch (ClassNotFoundException e) {
            log.warn("添加任务;request:{};ClassNotFoundException:{}", JsonUtil.toJsonString(dto), e.getMessage());
            e.printStackTrace();
        } catch (SchedulerException e) {
            log.warn("添加任务;request:{};SchedulerException:{}", JsonUtil.toJsonString(dto), e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 修改定时任务
     *
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateTask(TaskDto dto) {
        Task task = taskMapper.selectByPrimaryKey(dto.getId());
        try {
            quartzManager.updateCronExpression(task.getJobName(), task.getJobGroup(), dto);
        } catch (SchedulerException e) {
            log.warn("修改任务;request:{};SchedulerException:{}", JsonUtil.toJsonString(dto), e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            log.warn("修改任务;request:{};ClassNotFoundException:{}", JsonUtil.toJsonString(dto), e.getMessage());
            e.printStackTrace();
        }
        task.setUpdateTime(new Date());
        task.setJobStatus(dto.getJobStatus());
        task.setJobName(dto.getTaskName());
        task.setJobGroup(dto.getJobGroup());
        task.setCronExpression(dto.getCronExpression());
        task.setDescription(dto.getDescription());
        task.setBeanClass(dto.getBeanClass());
        task.setId(dto.getId());
        taskMapper.updateByPrimaryKeySelective(task);
    }

    /**
     * 分页获取任务列表
     *
     * @param offset
     * @param size
     * @return
     */
    @Override
    public PageInfo<Task> listTasks(int offset, int size) {
        PageHelper.offsetPage(offset, size);
        List<Task> taskList = taskMapper.selectAll();
        return new PageInfo<>(taskList);
    }

    /**
     * 任务初始化
     */
    @Override
    public void initTasks() {
        Task task = new Task();
        task.setJobStatus(JobStatusEnum.START.getCode());
        List<Task> activeTask = taskMapper.select(task);
        if (CollectionUtil.isEmpty(activeTask)) {
            return;
        }
        for (Task task1 : activeTask) {
            try {
                quartzManager.addTask(task1);
            } catch (ClassNotFoundException e) {
                log.warn("初始化任务;request:{};ClassNotFoundException:{}", JsonUtil.toJsonString(task1), e.getMessage());
                e.printStackTrace();
            } catch (SchedulerException e) {
                log.warn("初始化任务;request:{};SchedulerException:{}", JsonUtil.toJsonString(task1), e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除任务
     *
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeTask(long id) {
        Task task = taskMapper.selectByPrimaryKey(id);
        Assert.objectNotNull(task, "任务不存在");
        task.setJobStatus(JobStatusEnum.STOP.getCode());
        taskMapper.updateByPrimaryKeySelective(task);
        try {
            quartzManager.removeTask(task.getJobName(), task.getJobGroup());
        } catch (SchedulerException e) {
            log.warn("删除任务;id:{};SchedulerException:{}", id, e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 获取任务详细
     *
     * @param id
     * @return
     */
    @Override
    public Task getTaskById(long id) {
        return taskMapper.selectByPrimaryKey(id);
    }

    /**
     * 暂停任务
     *
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void pauseTask(long id) {
        Task task = taskMapper.selectByPrimaryKey(id);
        Assert.objectNotNull(task, "任务不存在");
        try {
            quartzManager.pauseTask(task.getJobName(), task.getJobGroup());
        } catch (SchedulerException e) {
            log.warn("暂停任务;id:{};task:{};SchedulerException:{}", id, task, e.getMessage());
            e.printStackTrace();
        }
        Task newTask = new Task();
        newTask.setId(task.getId());
        newTask.setJobStatus(JobStatusEnum.PAUSE.getCode());
        taskMapper.updateByPrimaryKeySelective(newTask);
        log.info("任务暂停;jobName:{};", task.getJobName());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void resumeTask(long id) {
        Task task = taskMapper.selectByPrimaryKey(id);
        Assert.objectNotNull(task, "任务不存在");
        Assert.stat(JobStatusEnum.STOP.getCode() == task.getJobStatus(), "已停止的任务不可恢复");
        try {
            quartzManager.resumeTask(task.getJobName(), task.getJobGroup());
        } catch (SchedulerException e) {
            log.warn("恢复任务;id:{};task:{};SchedulerException:{}", id, task, e.getMessage());
            e.printStackTrace();
        }
        Task newTask = new Task();
        newTask.setId(task.getId());
        newTask.setJobStatus(JobStatusEnum.START.getCode());
        taskMapper.updateByPrimaryKeySelective(newTask);
        log.info("恢复任务;jobName:{};", task.getJobName());
    }
}
