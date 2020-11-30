package website.lhc.heron.mapper;

import website.lhc.heron.framework.mybatis.BaseMapper;
import website.lhc.heron.model.SystemMonitor;

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
