package website.lhc.heron.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import website.lhc.heron.model.UserInfo;


/**
 * @description:
 * @author: 582895699@qq.com
 * @time: 2020/8/29 下午 11:30
 */
public abstract class AbstractController {
    public static UserInfo getUserInfo() {
        return (UserInfo) SecurityUtils.getSubject().getPrincipal();
    }

    public static Long getUerId() {
        return getUserInfo().getId();
    }

    /**
     * 获取session
     *
     * @return
     */
    public static Session getSession() {
        return getSubject().getSession();
    }

    /**
     * 获取当期对象
     *
     * @return
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static void setSessionValue(Object key, Object value) {
        getSession().setAttribute(key, value);
    }
}

