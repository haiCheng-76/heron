package webiste.lhc.heron.framework.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import webiste.lhc.heron.model.UserInfo;
import webiste.lhc.heron.service.MenuService;
import webiste.lhc.heron.service.UserInfoService;
import webiste.lhc.heron.util.CacheUtil;
import webiste.lhc.heron.util.PasswordUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.config.shiro
 * @ClassName: UserInfoRealm
 * @Author: lhc
 * @Description: 自定义realm
 * @Date: 2020/8/16 上午 11:06
 */
public class UserInfoRealm extends AuthorizingRealm {


    @Autowired
    private UserInfoService userInfoService;


    @Autowired
    private MenuService menuService;


    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserInfo userInfo = (UserInfo) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> permissionSet = new HashSet<>();
        if (1 == userInfo.getId()) {
            permissionSet.add("*:*:*");
        } else {
//            permissionSet = menuService.listPermissionByUserId(userInfo.getId());
            try {
//                permissionSet = (Set<String>) CacheUtil.getWithCallBack(String.valueOf(userInfo.getId() + "per"), new PermissionCallBack(userInfo.getId()));
                permissionSet = (Set<String>) CacheUtil.getWithCallBack(String.valueOf(userInfo.getId() + "permission"), new Callable() {
                    @Override
                    public Object call() throws Exception {
                        return menuService.listPermissionByUserId(userInfo.getId());
                    }
                });
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        authorizationInfo.setStringPermissions(permissionSet);
        return authorizationInfo;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = new String(token.getPassword());
        UserInfo currentUser = userInfoService.getUserByAccount(username);
        if (Objects.isNull(currentUser)) {
            throw new UnknownAccountException("用户名或密码错误");
        }
        if (currentUser.getIsDelete()) {
            throw new DisabledAccountException("账户已被注销");
        }
        if (currentUser.getEnable()) {
            throw new LockedAccountException("账号已被锁定");
        }

        if (!PasswordUtil.passwordMatch(password, currentUser.getSalt(), currentUser.getPassword())) {
            throw new IncorrectCredentialsException("用户名或密码错误");
        }
        ByteSource byteSource = ByteSource.Util.bytes(currentUser.getSalt());
        return new SimpleAuthenticationInfo(currentUser, currentUser.getPassword(), byteSource, getName());
    }
}
