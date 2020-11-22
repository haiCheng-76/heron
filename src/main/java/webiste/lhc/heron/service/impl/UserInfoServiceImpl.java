package webiste.lhc.heron.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.StringUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import webiste.lhc.heron.commo.enums.CacheEnum;
import webiste.lhc.heron.commo.exceptions.HeronException;
import webiste.lhc.heron.dto.UserVo;
import webiste.lhc.heron.mapper.UserInfoMapper;
import webiste.lhc.heron.model.UserInfo;
import webiste.lhc.heron.service.MailService;
import webiste.lhc.heron.service.UserInfoService;
import webiste.lhc.heron.util.CacheUtil;
import webiste.lhc.heron.util.JsonUtil;
import webiste.lhc.heron.util.PasswordUtil;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.Objects;

import static webiste.lhc.heron.util.Assert.objectNotNull;
import static webiste.lhc.heron.util.Assert.stat;

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

    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

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
        stat(Objects.nonNull(hasUser), "该用户已存在");
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
    public void updatePassword(String email, String ip) {
        UserInfo userInfo = new UserInfo();
        userInfo.setEMail(email);
        UserInfo user = userInfoMapper.selectOne(userInfo);
        objectNotNull(user, "邮箱地址不存在，无法重置密码");
        Object object = CacheUtil.get(CacheEnum.RESETPASSWORD, email);
        if (Objects.nonNull(object)) {
            long time = (long) object;
            long now = System.currentTimeMillis();
            stat(now - time < 6000, "邮件已发送，请稍后再试");
        }

        String randomPassword = new AlternativeJdkIdGenerator().generateId().toString().replaceAll("-", "");
        Context context = new Context();
        context.setVariable("account", user.getAccount());
        context.setVariable("newPassword", randomPassword);
        context.setVariable("to", email);
        context.setVariable("createDate", DateUtil.format(user.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        context.setVariable("ip", ip);
        context.setVariable("sendDate", DateUtil.now());
        String content = templateEngine.process("/mail", context);
        try {
            mailService.sendHtmlMail(email, "重置密码", content);
        } catch (MessagingException e) {
            log.warn("发送html邮件失败", e);
            e.printStackTrace();
            throw new HeronException("邮件发送失败，请稍后再试！");
        }
        String salt = new AlternativeJdkIdGenerator().generateId().toString();
        String newPassword = PasswordUtil.generatorPassword(randomPassword, salt);
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setSalt(salt);
        userInfo1.setPassword(newPassword);
        userInfo1.setUpdateTime(new Date());
        userInfo1.setId(user.getId());
        userInfoMapper.updateByPrimaryKeySelective(userInfo1);
        CacheUtil.set(CacheEnum.RESETPASSWORD, email, System.currentTimeMillis());
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
