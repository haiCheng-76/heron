package webiste.lhc.heron.service;

import webiste.lhc.heron.model.SystemMonitor;

public interface SystemMonitorService {
    void insertSystemMonitor();

    SystemMonitor getLastSystemMonitor();
}
