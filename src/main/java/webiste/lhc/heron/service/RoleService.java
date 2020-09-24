package webiste.lhc.heron.service;

import com.github.pagehelper.PageInfo;
import webiste.lhc.heron.model.RoleInfo;
import webiste.lhc.heron.vo.RoleVo;

/**
 * @description:
 * @author: 582895699@qq.com
 * @time: 2020/9/11 上午 12:01
 */
public interface RoleService {
    PageInfo<RoleInfo> pageRole(int current, int size);

    void insertRole(RoleVo roleVo);

    void deleteRole(long id);
}
