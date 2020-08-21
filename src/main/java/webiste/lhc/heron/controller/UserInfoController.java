package webiste.lhc.heron.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import webiste.lhc.heron.model.UserInfo;
import webiste.lhc.heron.service.UserInfoService;
import webiste.lhc.heron.util.Resp;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.controller
 * @ClassName: UserInfoController
 * @Author: lhc
 * @Description: TODO
 * @Date: 2020/8/16 下午 02:35
 */
@Controller
@RequestMapping(value = "userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @ResponseBody
    @PostMapping(value = "addUserInfo")
    public Resp addUserInfo(@RequestBody UserInfo user) {
        userInfoService.insertUserInfo(user);
        return Resp.ok();
    }
}
