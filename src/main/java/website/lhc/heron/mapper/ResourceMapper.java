package website.lhc.heron.mapper;

import website.lhc.heron.framework.mybatis.BaseMapper;
import website.lhc.heron.model.Resource;

import java.util.List;

public interface ResourceMapper extends BaseMapper<Resource> {
    List<String> resourceType();
}
