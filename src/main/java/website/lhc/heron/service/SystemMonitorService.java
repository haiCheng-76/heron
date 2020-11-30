package website.lhc.heron.service;

import website.lhc.heron.model.SystemMonitor;

import java.util.List;
import java.util.Map;

public interface SystemMonitorService {
    void insertSystemMonitor();

    SystemMonitor getLastSystemMonitor();

    List<Map<String, Object>> getMemoryData(int type);

    List<Long> getMemoryInfo(int type);
}
