package website.lhc.heron.service;

import com.github.pagehelper.PageInfo;
import website.lhc.heron.dto.UserVo;
import website.lhc.heron.model.UserInfo;

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

    void insertUserInfo(UserVo vo);

    PageInfo<UserInfo> pageUserInfo(int currnet, int size);

    UserInfo getUserById(long id);


    void updateUser(UserInfo userInfo);


    void deleteUser(long id);

    void updatePassword(String email, String ip);
}
