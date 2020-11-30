package website.lhc.heron.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;
import website.lhc.heron.service.MenuService;
import website.lhc.heron.service.UserInfoService;
import website.lhc.heron.util.Assert;
import website.lhc.heron.util.IpUtil;
import website.lhc.heron.util.Resp;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.controller
 * @ClassName: LoginController
 * @Author: lhc
 * @Description: TODO
 * @Date: 2020/8/16 上午 11:39
 */
@Api(tags = "通用接口")
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

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping(value = "login")
    public String login() {
        return "login";

    }

    Pattern pattern = Pattern.compile("^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");

    @ApiOperation(value = "用户登录")
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

    @ApiIgnore
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

    @ApiIgnore
    @GetMapping(value = "/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:login";
    }


    @ApiIgnore
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

    /**
     * 忘记密码页面跳转
     *
     * @return
     */
    @ApiIgnore
    @GetMapping(value = "/forgetPassword")
    public String forgetPassword() {
        return "forgot-password";
    }

    /**
     * 密码更新
     *
     * @return
     */
    @ApiOperation(value = "通过邮箱更新密码")
    @ResponseBody
    @GetMapping(value = "/createNewPassword")
    public Resp createNewPassword(@RequestParam(value = "email") String mail, HttpServletRequest request) {
        Matcher matcher = pattern.matcher(mail);
        boolean match = matcher.matches();
        Assert.stat(!match, "请输入正确的邮箱地址");
        userInfoService.updatePassword(mail, IpUtil.getIpFromRequest(request));
        return Resp.ok();
    }
}
