package website.lhc.heron.framework.quartz;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import website.lhc.heron.dto.TaskDto;
import website.lhc.heron.model.Task;
import website.lhc.heron.util.JsonUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class QuartzManager {

    private static final String DEFAULT_TRIGGER_NAME = "default_trigger_name";
    private static final String DEFAULT_TRIGGER_GROUP = "default_trigger_group";
    @Autowired
    private Scheduler scheduler;

    /**
     * 创建任务
     *
     * @param task
     * @throws ClassNotFoundException
     * @throws SchedulerException
     */
    public void addTask(Task task) throws ClassNotFoundException, SchedulerException {
        String beanClass = task.getBeanClass().trim();
        Class clazz = Class.forName(beanClass);
        // 定义任务
        JobDetail jobDetail = JobBuilder.newJob(clazz)
                .withIdentity(task.getJobName(), task.getJobGroup())
                .build();
        // 定义表达式
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpression().trim());
        // 触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(DEFAULT_TRIGGER_NAME + "_" + task.getJobName(), DEFAULT_TRIGGER_GROUP + "_" + task.getJobGroup())
                .startNow()
                .withSchedule(cronScheduleBuilder)
                .withDescription(task.getDescription())
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
        if (scheduler.isShutdown()) {
            scheduler.start();
        }
        log.info("添加定时任务;task:{};triggerName:{};triggerGroup:{};startTime:{}", JSONUtil.toJsonStr(task), DEFAULT_TRIGGER_NAME + "_" + task.getJobName(), DEFAULT_TRIGGER_GROUP + "_" + task.getJobGroup(), DateUtil.now());
    }

    /**
     * 获取原来的job，并重新放入
     *
     * @param taskName
     * @param dto
     * @throws SchedulerException
     */
    public void updateCronExpression(String taskName, String groupName, TaskDto dto) throws SchedulerException, ClassNotFoundException {
        log.info("修改定时任务;taskName:{};dto:{}", taskName, JsonUtil.toJsonString(dto));
        TriggerKey triggerKey = TriggerKey.triggerKey(DEFAULT_TRIGGER_NAME + "_" + taskName, DEFAULT_TRIGGER_GROUP + "_" + groupName);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (cronTrigger == null) {
            return;
        }
        String beanClass = dto.getBeanClass();
        Class clazz = Class.forName(beanClass);
        JobDetail jobDetail = JobBuilder.newJob(clazz)
                .withIdentity(dto.getTaskName(), dto.getJobGroup())
                .build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(dto.getCronExpression());
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(DEFAULT_TRIGGER_NAME + "_" + taskName, DEFAULT_TRIGGER_GROUP + "_" + groupName)
                .startNow()
                .withSchedule(cronScheduleBuilder)
                .withDescription(dto.getDescription())
                .build();
        scheduler.pauseTrigger(triggerKey);
        scheduler.unscheduleJob(triggerKey);
        scheduler.scheduleJob(jobDetail, trigger);
        if (scheduler.isShutdown()) {
            scheduler.start();
        }
    }

    /**
     * 移除任务
     *
     * @param taskName
     * @throws SchedulerException
     */
    public void removeTask(String taskName, String group) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(DEFAULT_TRIGGER_NAME + "_" + taskName, DEFAULT_TRIGGER_GROUP + "_" + group);
        JobKey jobKey = JobKey.jobKey(taskName, group);
        Trigger trigger = scheduler.getTrigger(triggerKey);
        if (trigger == null) {
            return;
        }
        scheduler.pauseTrigger(triggerKey);
        scheduler.unscheduleJob(triggerKey);
        scheduler.deleteJob(jobKey);
        log.info("移除定时任务;taskName:{}", taskName);
    }

    /**
     * 暂停任务
     *
     * @param taskName
     * @throws SchedulerException
     */
    public void pauseTask(String taskName, String group) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(taskName, group);
        if (Objects.isNull(jobKey)) {
            return;
        }
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复一个任务
     *
     * @param taskName
     * @throws SchedulerException
     */
    public void resumeTask(String taskName, String group) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(taskName, group);
        if (Objects.isNull(jobKey)) {
            return;
        }
        scheduler.resumeJob(jobKey);
    }

    /**
     * @throws SchedulerException
     */
    public void startAllTask() throws SchedulerException {
        scheduler.start();
    }

    /**
     * 关闭所有任务
     *
     * @throws SchedulerException
     */
    public void stopAllTask() throws SchedulerException {
        if (!scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }

    /**
     * 获取任务对于的状态
     *
     * @param taskName
     * @return
     * @throws SchedulerException
     */
    public String getTaskStstus(String taskName) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(taskName);
        Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);
        return triggerState.name();
    }

    /**
     * 获取指定次数的时间执行计划
     *
     * @param cronExpression
     * @param size
     * @return
     * @throws ParseException
     */
    public List<String> getRecentTaskTime(String cronExpression, Integer size) throws ParseException {
        CronTriggerImpl cronTrigger = new CronTriggerImpl();
        cronTrigger.setCronExpression(cronExpression);
        Integer cronSize = Objects.isNull(size) ? 5 : size;
        List<String> list = new ArrayList<>(cronSize);
        List<Date> dateList = TriggerUtils.computeFireTimes(cronTrigger, null, cronSize);
        for (Date date : dateList) {
            list.add(DateUtil.formatDate(date));
        }
        return list;
    }
}
