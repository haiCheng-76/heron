package webiste.lhc.heron.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import webiste.lhc.heron.service.MenuService;
import webiste.lhc.heron.util.JsonUtil;
import webiste.lhc.heron.vo.MenuVo;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
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
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);


    @Autowired
    private MenuService menuService;

    @GetMapping(value = "login")
    public String login() {
        return "login";
    }

    @ResponseBody
    @PostMapping(value = "login")
    public Map<String, Object> login(String account, String password, HttpSession session) {
        Map<String, Object> map = new HashMap<>(2);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        try {
            subject.login(token);
//            session.setAttribute();
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


    @GetMapping(value = "index_iframe")
    public String dashbroad(Model model) {
        model.addAttribute("message", "11111111111111111111");
        return "index_iframe";
    }

    @GetMapping(value = {"/", "index"})
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
//        List<MenuVo> menuVoList = menuService.listMenus();
//        modelAndView.addObject("menus", menuVoList);
//        log.info("result:{}", JsonUtil.toJsonString(menuVoList));
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping(value = "header")
    public ModelAndView header() {
//        ModelAndView model = new ModelMap();
        List<MenuVo> menuVoList = menuService.listMenus();
//        model.addAttribute("menus", menuVoList);
//        return model;
//        modelAndView.addObject("menus", menuVoList);
//        log.info("result:{}", JsonUtil.toJsonString(menuVoList));
//        modelAndView.setViewName("index");
//        return modelAndView;
        return null;
    }

    @GetMapping(value = {"welcome"})
    public String welcome() {
        return "welcome";
    }
}
