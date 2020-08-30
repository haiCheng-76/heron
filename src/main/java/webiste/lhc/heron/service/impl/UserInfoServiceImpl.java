package webiste.lhc.heron.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AlternativeJdkIdGenerator;
import webiste.lhc.heron.mapper.UserInfoMapper;
import webiste.lhc.heron.model.UserInfo;
import webiste.lhc.heron.service.UserInfoService;
import webiste.lhc.heron.util.Assert;
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
    public void insertUserInfo(UserInfo userInfo) {
        UserInfo hasUser = getUserByAccount(userInfo.getAccount());
        Assert.stat(Objects.nonNull(hasUser), "该用户已存在");
        String salt = new AlternativeJdkIdGenerator().generateId().toString();
        String password = PasswordUtil.generatorPassword(userInfo.getPassword(), salt);
        userInfo.setCreateTime(new Date());
        userInfo.setPassword(password);
        userInfo.setSalt(salt);
        userInfoMapper.insertSelective(userInfo);
    }
}
