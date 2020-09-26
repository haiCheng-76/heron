package webiste.lhc.heron.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.StringUtils;
import webiste.lhc.heron.dto.UserVo;
import webiste.lhc.heron.mapper.UserInfoMapper;
import webiste.lhc.heron.model.UserInfo;
import webiste.lhc.heron.service.UserInfoService;
import webiste.lhc.heron.util.Assert;
import webiste.lhc.heron.util.JsonUtil;
import webiste.lhc.heron.util.PasswordUtil;

import java.util.Date;
import java.util.Objects;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.service
 * @ClassName: UserInfoServiceImpl
 * @Author: lhc
 * @Description: TODO
 * @Date: 2020/8/16 下午 12:26
 */

@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getUserByAccount(String account) {
        UserInfo userInfo = new UserInfo();
        userInfo.setAccount(account);
        userInfo.setIsDelete(false);
        return userInfoMapper.selectOne(userInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertUserInfo(UserVo vo) {
        log.info("添加用户;userVo:{}", JsonUtil.toJsonString(vo));
        UserInfo user = new UserInfo();
        UserInfo hasUser = getUserByAccount(vo.getAccount());
        Assert.stat(Objects.nonNull(hasUser), "该用户已存在");
        String salt = new AlternativeJdkIdGenerator().generateId().toString();
        String password = PasswordUtil.generatorPassword(vo.getPassword(), salt);
        user.setCreateTime(new Date());
        user.setPassword(password);
        user.setSalt(salt);
        user.setAccount(vo.getAccount());
        user.setUserName(vo.getUserName());
        userInfoMapper.insertSelective(user);
        userInfoMapper.addUserRole(vo.getRoleId(), user.getId());
    }

    @Override
    public PageInfo<UserInfo> pageUserInfo(int current, int size) {
        PageHelper.offsetPage(current, size);
        return new PageInfo<>(userInfoMapper.select(new UserInfo(false)));
    }

    @Override
    public UserInfo getUserById(long id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteUser(long id) {
        UserInfo userInfo = new UserInfo();
        userInfo.setIsDelete(true);
        userInfo.setUpdateTime(new Date());
        userInfo.setId(id);
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    @Override
    public void updateUser(UserInfo userInfo) {
        if (StringUtils.hasText(userInfo.getPassword())) {
            String salt = new AlternativeJdkIdGenerator().generateId().toString();
            String password = PasswordUtil.generatorPassword(userInfo.getPassword(), salt);
            userInfo.setSalt(salt);
            userInfo.setPassword(password);
        }
        userInfo.setUpdateTime(new Date());
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }
}
