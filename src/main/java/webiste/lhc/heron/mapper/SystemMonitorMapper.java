package webiste.lhc.heron.mapper;

import webiste.lhc.heron.framework.mybatis.BaseMapper;
import webiste.lhc.heron.model.SystemMonitor;

public interface SystemMonitorMapper extends BaseMapper<SystemMonitor> {

    SystemMonitor getLastMonitor();
}