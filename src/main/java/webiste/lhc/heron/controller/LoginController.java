package webiste.lhc.heron.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.controller
 * @ClassName: LoginController
 * @Author: lhc
 * @Description: TODO
 * @Date: 2020/8/16 上午 11:39
 */
@Controller
public class LoginController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @GetMapping(value = "login")
    public String login() {
        return "login";
    }

    @ResponseBody
    @PostMapping(value = "login")
    public Map<String, Object> login(String account, String password) {
        Map<String, Object> map = new HashMap<>(2);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        try {
            subject.login(token);
            log.info("登录成功");
        } catch (AuthenticationException e) {
            map.put("code", 500);
            if (e instanceof AccountException) {
                map.put("message", e.getMessage());

            } else {
                map.put("message", "用户名或密码错误，请稍后再试！");
            }
            log.warn("用户登录失败；account:[{}];message:[{}],time:[{}]", account, e.getMessage(), System.currentTimeMillis());
            return map;

        }
        map.put("messge", "success");
        map.put("code", 200);
        return map;
    }

    @GetMapping(value = {"index"})
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping(value = "/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:login";
    }
}
