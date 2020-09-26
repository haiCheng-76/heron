package webiste.lhc.heron.mapper;

import org.apache.ibatis.annotations.Param;
import webiste.lhc.heron.config.mapperconfig.BaseMapper;
import webiste.lhc.heron.model.RoleInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RoleInfoMapper extends BaseMapper<RoleInfo> {
    void addRolePer(@Param(value = "roleId") long roleId, @Param(value = "ids") List<Integer> ids);

    void delRolePer(@Param(value = "roleId") long roleId);

    Set<Long> getPermissionIds(long roleId);

    void delRolePermission(long roleId);

    List<Map<String, Object>> listRoleMap();
}
