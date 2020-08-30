package webiste.lhc.heron.controller;

import org.apache.shiro.SecurityUtils;
import webiste.lhc.heron.model.UserInfo;

/**
 * @description:
 * @author: 582895699@qq.com
 * @time: 2020/8/29 下午 11:30
 */
public abstract class AbstractController {
    public UserInfo getUserInfo() {
        return (UserInfo) SecurityUtils.getSubject().getPrincipal();
    }

    public Long getUerId() {
        return getUserInfo().getId();
    }
}
