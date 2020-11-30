package website.lhc.heron.framework.quartz.jobs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import website.lhc.heron.service.SystemMonitorService;

@Slf4j
@Component
public class MonitorJob implements Job {

    @Autowired

    private SystemMonitorService systemMonitorService;

    @Override
    public void execute(JobExecutionContext context) {
        long start = System.currentTimeMillis();
        log.info("开始系统监控统计...");
        systemMonitorService.insertSystemMonitor();
        log.info("系统监控统计结束;time:{}ms", System.currentTimeMillis() - start);
    }
}
