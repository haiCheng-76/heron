package webiste.lhc.heron.mapper;

import org.apache.ibatis.annotations.Param;
import webiste.lhc.heron.config.mapperconfig.BaseMapper;
import webiste.lhc.heron.model.RoleInfo;

import javax.swing.*;
import java.util.List;

public interface RoleInfoMapper extends BaseMapper<RoleInfo> {
    void addRolePer(@Param(value = "roleId") long roleId,@Param(value = "ids") List<Integer> ids);
    void delRolePer(@Param(value = "roleId") long roleId);
}
