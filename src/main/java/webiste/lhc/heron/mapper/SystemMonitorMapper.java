package webiste.lhc.heron.mapper;

import webiste.lhc.heron.framework.mybatis.BaseMapper;
import webiste.lhc.heron.model.SystemMonitor;

import java.util.List;
import java.util.Map;

public interface SystemMonitorMapper extends BaseMapper<SystemMonitor> {

    SystemMonitor getLastMonitor(String javaVersion);

    /**
     * 通过java版本获取charts数据
     *
     * @param javaVersion
     * @return
     */
    List<Map<String, Object>> listMemoryCharts(String javaVersion);

    Map<String, Long> getMemoryInfo(String javaVersion);

    List<Map<String, Object>> listJvmMemoryCharts(String javaVersion);

    Map<String, Long> getJvmMemoryInfo(String javaVersion);
}