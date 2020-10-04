package webiste.lhc.heron.framework.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import webiste.lhc.heron.service.TaskService;

@Slf4j
public class TaskInit implements CommandLineRunner {

    @Autowired
    private TaskService taskService;
    @Override
    public void run(String... args) throws Exception {
        log.info("定时任务初始化中...");
        taskService.initTasks();
        log.info("定时任务初始化结束");
    }
}
