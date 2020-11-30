package website.lhc.heron.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import website.lhc.heron.mapper.RoleInfoMapper;
import website.lhc.heron.model.RoleInfo;
import website.lhc.heron.service.RoleService;
import website.lhc.heron.vo.RoleVo;

import java.util.*;
import java.util.List;

/**
 * @description:
 * @author: 582895699@qq.com
 * @time: 2020/9/11 上午 12:01
 */
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleInfoMapper roleInfoMapper;

    @Override
    public PageInfo<RoleInfo> pageRole(int current, int size) {
        PageHelper.offsetPage(current, size);
        Example example = new Example(RoleInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deleted", false);
        return new PageInfo<>(roleInfoMapper.selectByExample(example));
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertRole(RoleVo roleVo) {
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setCreateTime(new Date());
        roleInfo.setDeleted(false);
        roleInfo.setDescription(roleVo.getRemark());
        roleInfo.setRoleName(roleVo.getRoleName());
        roleInfo.setSort(roleVo.getSort());
        roleInfoMapper.insert(roleInfo);
        roleInfoMapper.addRolePer(roleInfo.getId(), roleVo.getIds());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteRole(long id) {
        roleInfoMapper.deleteByPrimaryKey(id);
        roleInfoMapper.delRolePer(id);
    }

    @Override
    public RoleInfo getRoleInfoById(long id) {
        RoleInfo roleInfo = roleInfoMapper.selectByPrimaryKey(id);
        Set<Long> ids = roleInfoMapper.getPermissionIds(id);
        roleInfo.setPermissionIds(ids);
        return roleInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRoleInfo(RoleVo vo) {
        // 修改role信息
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setSort(vo.getSort());
        roleInfo.setUpdateTime(new Date());
        roleInfo.setRoleName(vo.getRoleName());
        roleInfo.setDescription(vo.getRemark());
        roleInfo.setId(vo.getId());
        roleInfoMapper.updateByPrimaryKeySelective(roleInfo);
        // 删除之前的角色-权限对应关系
        roleInfoMapper.delRolePermission(vo.getId());
        // 重新插入对应关系
        roleInfoMapper.addRolePer(vo.getId(), vo.getIds());
    }

    @Override
    public List<Map<String, Object>> listRole() {
        return roleInfoMapper.listRoleMap();
    }
}
