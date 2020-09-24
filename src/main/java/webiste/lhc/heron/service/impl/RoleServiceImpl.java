package webiste.lhc.heron.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import webiste.lhc.heron.mapper.RoleInfoMapper;
import webiste.lhc.heron.model.RoleInfo;
import webiste.lhc.heron.service.RoleService;
import webiste.lhc.heron.vo.RoleVo;

import java.util.Date;

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
}
