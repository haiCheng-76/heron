package website.lhc.heron.framework.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import website.lhc.heron.mapper.LogsMapper;
import website.lhc.heron.mapper.UserInfoMapper;
import website.lhc.heron.model.Logs;
import website.lhc.heron.model.UserInfo;
import website.lhc.heron.util.IpUtil;
import website.lhc.heron.util.UserSession;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.config.shiro
 * @ClassName: AuthentFilter
 * @Author: lhc
 * @Description: TODO
 * @Date: 2020/8/27 上午 12:21
 */
public class AuthentFilter extends FormAuthenticationFilter {


    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private LogsMapper logsMapper;


    private static final Logger log = LoggerFactory.getLogger(AuthentFilter.class);

    private static final String USER_ACCOUNT = "account";

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        String userName = (String) token.getPrincipal();
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpSession httpSession = httpServletRequest.getSession();
        UserInfo userInfo = userInfoMapper.selectOne(new UserInfo(userName));
        UserSession userSession = new UserSession();
        userSession.setUserName(userInfo.getUserName());
        userSession.setAccount(userInfo.getAccount());
        userSession.setUserId(userInfo.getId());
        userSession.setAvatar(userInfo.getAvatar());
        userSession.seteMail(userInfo.getEMail());
        httpSession.setAttribute("currentUser", userSession);
        String ip = IpUtil.getIpFromRequest((HttpServletRequest) request);
        Logs logs = new Logs();
        logs.setIp(ip);
        logs.setTime(new Date());
        logs.setAccount(userInfo.getAccount());
        logsMapper.insertUseGeneratedKeys(logs);
        return super.onLoginSuccess(token, subject, request, response);
    }

    @Override
    public String getUsernameParam() {
        return USER_ACCOUNT;
    }
}
