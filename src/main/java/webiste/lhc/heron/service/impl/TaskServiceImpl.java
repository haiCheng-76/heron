package webiste.lhc.heron.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webiste.lhc.heron.commo.enums.JobStatusEnum;
import webiste.lhc.heron.dto.TaskDto;
import webiste.lhc.heron.framework.quartz.QuartzManager;
import webiste.lhc.heron.mapper.TaskMapper;
import webiste.lhc.heron.model.Task;
import webiste.lhc.heron.service.TaskService;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: 582895699@qq.com
 * @time: 2020/9/28 下午 10:53
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private QuartzManager quartzManager;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addTask(TaskDto dto) {
//        UserInfo userInfo = ShiroUtil.getUserInfo();
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
            e.printStackTrace();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTask(TaskDto dto) {

    }

    @Override
    public PageInfo<Task> listTasks(int offset, int size) {
        PageHelper.offsetPage(offset, size);
        List<Task> taskList = taskMapper.selectAll();
        return new PageInfo<>(taskList);
    }

    @Override
    public void initTasks() {
        Task task = new Task();
        task.setJobStatus(JobStatusEnum.start.getCode());
        List<Task> activeTask = taskMapper.select(task);
        if (CollectionUtil.isEmpty(activeTask)) {
            return;
        }
        for (Task task1 : activeTask) {
            try {
                quartzManager.addTask(task1);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeTask(long id) {
        Task task = new Task();
        task.setId(id);
        Task currentTask = taskMapper.selectOne(task);
        task.setJobStatus(JobStatusEnum.stop.getCode());
        taskMapper.updateByPrimaryKeySelective(task);
        try {
            quartzManager.removeTask(currentTask.getJobName());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
