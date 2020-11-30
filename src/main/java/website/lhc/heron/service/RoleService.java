package website.lhc.heron.service;

import com.github.pagehelper.PageInfo;
import website.lhc.heron.model.RoleInfo;
import website.lhc.heron.vo.RoleVo;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 582895699@qq.com
 * @time: 2020/9/11 上午 12:01
 */
public interface RoleService {
    PageInfo<RoleInfo> pageRole(int current, int size);

    void insertRole(RoleVo roleVo);

    void deleteRole(long id);

    RoleInfo getRoleInfoById(long id);

    void updateRoleInfo(RoleVo roleVo);

    List<Map<String, Object>> listRole();
}
