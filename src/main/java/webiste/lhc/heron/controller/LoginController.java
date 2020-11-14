package webiste.lhc.heron.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import webiste.lhc.heron.service.MenuService;
import webiste.lhc.heron.util.IpUtil;

import javax.servlet.http.HttpServletRequest;


/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.controller
 * @ClassName: LoginController
 * @Author: lhc
 * @Description: TODO
 * @Date: 2020/8/16 上午 11:39
 */
@Slf4j
@Controller
public class LoginController extends AbstractController {

    @Value(value = "${gaode.ipInfo.url}")
    private String ipUrl;

    @Value(value = "${gaode.appkey}")
    private String appKey;

    @Value(value = "${gaode.weather.url}")
    private String weatherUrl;


    @Autowired
    private MenuService menuService;

    @GetMapping(value = "login")
    public String login() {
        return "login";
    }

    @PostMapping(value = "login")
    public String login(String account, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        try {
            subject.login(token);
            log.info("登录成功;account:{}", account);
        } catch (AuthenticationException e) {
            setSessionValue("loginError", e.getMessage());
            return "/login";
        }
        return "redirect:/index";
    }

    @GetMapping(value = {"/index", "/"})
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("menus", menuService.getMenuByUserId(getUerId()));
        String iputil = IpUtil.getIpFromRequest(request);
        String ipAddress = request.getRemoteHost();
        log.info("iputil:{};ipAddress:{}", iputil, ipAddress);
        String url = ipUrl + "?key=" + appKey + "&ip" + ipAddress;
        HttpResponse response = HttpRequest.get(url).execute();
        JsonObject ipJsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
        String province = ipJsonObject.get("province").getAsString();
        String city = ipJsonObject.get("city").getAsString();
        String adcode = ipJsonObject.get("adcode").getAsString();
        log.info("province:{};city:{},adcode:{}", province, city, adcode);
        String weather = weatherUrl + "?city=" + adcode + "&key=" + appKey + "&extensions=base";
        HttpResponse weatherResponse = HttpRequest.get(weather).execute();
        if (weatherResponse.isOk()) {
            log.info("weatherUrl:{};weatherResponse:{}", weather, JSONUtil.toJsonStr(weatherResponse));
        }
        return modelAndView;
    }

    @GetMapping(value = "/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:login";
    }

    @GetMapping(value = "getSummaryInfo")
    @ResponseBody
    public String getSummaryInfo(HttpServletRequest request) {
        String ipAddress = IpUtil.getIpFromRequest(request);
        String url = ipUrl + "?key=" + appKey + "&ip" + ipAddress;
        HttpResponse response = HttpRequest.get(url).execute();
        JsonObject ipJsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
        String province = ipJsonObject.get("province").getAsString();
        String city = ipJsonObject.get("city").getAsString();
        String adcode = ipJsonObject.get("adcode").getAsString();

        String weather = weatherUrl + "?city=" + adcode + "&key=" + appKey;
        HttpResponse weatherResponse = HttpRequest.get(weather).execute();
        if (weatherResponse.isOk()) {
            log.info("weatherResponse:{}", JSONUtil.toJsonStr(weatherResponse));
        }
        return "success";
    }
}
