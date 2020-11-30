package website.lhc.heron.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import website.lhc.heron.model.UserInfo;

public class ShiroUtil {

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static UserInfo getUserInfo() {
        return (UserInfo) getSubject();
    }
}
