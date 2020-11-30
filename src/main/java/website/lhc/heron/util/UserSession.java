package website.lhc.heron.util;

import java.io.Serializable;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.util
 * @ClassName: UserSeeesion
 * @Author: lhc
 * @Description: TODO
 * @Date: 2020/8/27 下午 11:28
 */
public class UserSession implements Serializable {

    private static final long serialVersionUID = 7519889379674716048L;
    private Long userId;
    private String userName;
    private String eMail;
    private String avatar;
    private String account;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return JsonUtil.toJsonString(this);
    }
}
