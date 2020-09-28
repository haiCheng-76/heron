package webiste.lhc.heron.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import webiste.lhc.heron.service.MenuService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
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


    @Autowired
    private MenuService menuService;

    @GetMapping(value = "login")
    public String login() {
        return "login";
    }

    //    @ResponseBody
    @PostMapping(value = "login")
    public String login(String account, String password) {
        Map<String, Object> map = new HashMap<>(2);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        try {
            subject.login(token);
            log.info("登录成功");
        } catch (AuthenticationException e) {
            return "/login";
        }
        return "redirect:/index";
    }

    @GetMapping(value = {"/index", "/"})
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("menus", menuService.getMenuByUserId(getUerId()));
        return modelAndView;
    }

    @GetMapping(value = "/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:login";
    }
}
