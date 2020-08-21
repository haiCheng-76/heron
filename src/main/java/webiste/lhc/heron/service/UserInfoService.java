package webiste.lhc.heron.service;

import webiste.lhc.heron.model.UserInfo;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.service
 * @ClassName: UserInfoService
 * @Author: lhc
 * @Description: TODO
 * @Date: 2020/8/16 下午 12:25
 */
public interface UserInfoService {
    UserInfo getUserByAccount(String account);

    void insertUserInfo(UserInfo userInfo);
}
