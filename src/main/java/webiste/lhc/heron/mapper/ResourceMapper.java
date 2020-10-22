package webiste.lhc.heron.mapper;

import webiste.lhc.heron.framework.mybatis.BaseMapper;
import webiste.lhc.heron.model.Resource;

import java.util.List;

public interface ResourceMapper extends BaseMapper<Resource> {
    List<String> resourceType();
}