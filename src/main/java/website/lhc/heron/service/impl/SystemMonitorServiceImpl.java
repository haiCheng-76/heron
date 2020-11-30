package website.lhc.heron.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.util.FormatUtil;
import website.lhc.heron.mapper.SystemMonitorMapper;
import website.lhc.heron.model.SystemMonitor;
import website.lhc.heron.service.SystemMonitorService;

import java.text.DecimalFormat;
import java.util.*;


@Slf4j

@Service
public class SystemMonitorServiceImpl implements SystemMonitorService {
    @Autowired
    private SystemMonitorMapper systemMonitorMapper;
    static final SystemInfo systemInfo = new SystemInfo();

    @Scope(value = "prototype")
    @Override
    public void insertSystemMonitor() {
        SystemMonitor systemMonitor = new SystemMonitor();

        Properties props = System.getProperties();

        // cpu
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;

        log.info("a:{};b:{};c:{};d:{};e:{}", processor.getLogicalProcessorCount(), new DecimalFormat("#.##%").format(cSys * 1.0 / totalCpu), new DecimalFormat("#.##%").format(user * 1.0 / totalCpu)
                , new DecimalFormat("#.##%").format(iowait * 1.0 / totalCpu), new DecimalFormat("#.##%").format(1.0 - (idle * 1.0 / totalCpu)));

        systemMonitor.setCpuCoreCount(processor.getLogicalProcessorCount());
//        String cpuSysUsage = new DecimalFormat("#.##%").format(cSys * 1.0 / totalCpu);
        systemMonitor.setCpuSysUsage(new DecimalFormat("#.##%").format(cSys * 1.0 / totalCpu));
        systemMonitor.setCpuUserUsage(new DecimalFormat("#.##%").format(user * 1.0 / totalCpu));
        systemMonitor.setCpuWaitUsage(new DecimalFormat("#.##%").format(iowait * 1.0 / totalCpu));
        systemMonitor.setCpuUsage(new DecimalFormat("#.##%").format(1.0 - (idle * 1.0 / totalCpu)));
        // 内存
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        long totalByte = memory.getTotal();
        long avaliableByte = memory.getAvailable();
        systemMonitor.setMemoryTotal(totalByte);
        systemMonitor.setMemoryAvailable(avaliableByte);
        systemMonitor.setMemoryUsed(totalByte - avaliableByte);
        systemMonitor.setMemoryUsage(new DecimalFormat("#.##%").format((totalByte - avaliableByte) * 1.0 / totalByte));
        //jvm

        Runtime runtime = Runtime.getRuntime();
        //jvm总内存
        long jvmTotalMemoryByte = runtime.totalMemory();
        //空闲空间
        long freeMemoryByte = runtime.freeMemory();
        //jdk版本
        String jdkVersion = props.getProperty("java.version");
        //jdk路径
        String jdkHome = props.getProperty("java.home");
        systemMonitor.setJvmMemoryTotal(jvmTotalMemoryByte);
        systemMonitor.setJvmMemoryAvaliable(freeMemoryByte);
        systemMonitor.setJvmMemoryUsed(jvmTotalMemoryByte - freeMemoryByte);
        systemMonitor.setJvmMemoryUsage(new DecimalFormat("#.##%").format((jvmTotalMemoryByte - freeMemoryByte) * 1.0 / jvmTotalMemoryByte));
        systemMonitor.setJavaVersion(jdkVersion);
        // 服务器
        //系统名称
        String osName = props.getProperty("os.name");
        //架构名称
        String osArch = props.getProperty("os.arch");
        systemMonitor.setOsName(osName);
        systemMonitor.setOsArch(osArch);

        systemMonitor.setCreateTime(new Date());
        if (log.isDebugEnabled()) {
            log.debug("monitorData:{}", JSONUtil.toJsonStr(systemMonitor));
        }
        systemMonitorMapper.insertUseGeneratedKeys(systemMonitor);
    }

    /**
     * 获取当前设备最新的系统信息
     *
     * @return
     */
    @Override
    public SystemMonitor getLastSystemMonitor() {
        return systemMonitorMapper.getLastMonitor(System.getProperty("java.version"));
    }

    /**
     * 获取当前设备的内存波动信息
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getMemoryData(int type) {
        // jvm内存信息
        if (1 == type) {
            List<Map<String, Object>> mapList = systemMonitorMapper.listJvmMemoryCharts(System.getProperty("java.version"));
            if (CollectionUtil.isEmpty(mapList)) {
                return Collections.emptyList();
            }
            for (Map<String, Object> stringLongMap : mapList) {
                long y = Long.parseLong(String.valueOf(stringLongMap.get("y")));
                stringLongMap.put("y", FormatUtil.formatBytes(y));
            }
            return mapList;
        } else {
            List<Map<String, Object>> mapList = systemMonitorMapper.listMemoryCharts(System.getProperty("java.version"));
            if (CollectionUtil.isEmpty(mapList)) {
                return Collections.emptyList();
            }
            for (Map<String, Object> stringLongMap : mapList) {
                long y = Long.parseLong(String.valueOf(stringLongMap.get("y")));
                stringLongMap.put("y", FormatUtil.formatBytes(y));
            }
            return mapList;
        }
    }

    @Override
    public List<Long> getMemoryInfo(int type) {
        List<Long> list = new ArrayList<>(2);
        // jvm内存信息
        if (1 == type) {
            Map<String, Long> memoryInfo = systemMonitorMapper.getJvmMemoryInfo(System.getProperty("java.version"));
            if (CollectionUtil.isEmpty(memoryInfo)) {
                return Collections.emptyList();
            }
            list.add(memoryInfo.get("used"));
            list.add(memoryInfo.get("available"));
            return list;
        } else {
            Map<String, Long> memoryInfo = systemMonitorMapper.getMemoryInfo(System.getProperty("java.version"));
            if (CollectionUtil.isEmpty(memoryInfo)) {
                return Collections.emptyList();
            }
            list.add(memoryInfo.get("used"));
            list.add(memoryInfo.get("available"));
            return list;
        }
    }
}
