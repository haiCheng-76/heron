package webiste.lhc.heron.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import webiste.lhc.heron.mapper.RoleInfoMapper;
import webiste.lhc.heron.model.RoleInfo;
import webiste.lhc.heron.service.RoleService;

/**
 * @description:
 * @author: 582895699@qq.com
 * @time: 2020/9/11 上午 12:01
 */
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
}
