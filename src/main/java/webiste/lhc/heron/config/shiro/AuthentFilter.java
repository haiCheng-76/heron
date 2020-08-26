package webiste.lhc.heron.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.config.shiro
 * @ClassName: AuthentFilter
 * @Author: lhc
 * @Description: TODO
 * @Date: 2020/8/27 上午 12:21
 */
public class AuthentFilter extends FormAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(AuthentFilter.class);

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        Object userName = token.getPrincipal();
        log.info("userName:{}", userName);
        return super.onLoginSuccess(token, subject, request, response);
    }
}
