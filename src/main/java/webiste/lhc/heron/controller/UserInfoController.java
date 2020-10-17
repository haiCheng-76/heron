package webiste.lhc.heron.controller;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import webiste.lhc.heron.dto.UserVo;
import webiste.lhc.heron.model.UserInfo;
import webiste.lhc.heron.service.MenuService;
import webiste.lhc.heron.service.RoleService;
import webiste.lhc.heron.service.UserInfoService;
import webiste.lhc.heron.util.Resp;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.controller
 * @ClassName: UserInfoController
 * @Author: lhc
 * @Description: 用户相关
 * @Date: 2020/8/16 下午 02:35
 */
@Slf4j
@Controller
@RequestMapping(value = "userInfo")
public class UserInfoController extends AbstractController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @ResponseBody
    @PostMapping(value = "addUserInfo")
    public Resp addUserInfo(@RequestBody UserVo vo) {
        userInfoService.insertUserInfo(vo);
        return Resp.ok();
    }

    @GetMapping(value = "/viewUsers")
    public ModelAndView viewUsers(@RequestParam(value = "menuName", required = false, defaultValue = "Heron") String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/userList");
        modelAndView.addObject("text", name);
        modelAndView.addObject("menus", menuService.getMenuByUserId(getUerId()));
        return modelAndView;
    }

    @ResponseBody
    @GetMapping(value = "pageUserInfo")
    public PageInfo<UserInfo> pageUserInfo(@RequestParam(value = "offset") int current,
                                            @RequestParam(value = "limit") int size) {
        PageInfo<UserInfo> pageInfo = userInfoService.pageUserInfo(current, size);
        return pageInfo;
    }

    @GetMapping(value = "getUserInfo")
    public ModelAndView getUserInfo(@RequestParam(value = "id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/userDetail");
        modelAndView.addObject("user", userInfoService.getUserById(id));
        return modelAndView;
    }


    @GetMapping(value = "updateUserInfo")
    public ModelAndView updateUserInfo(@RequestParam(value = "id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/updateUser");
        modelAndView.addObject("user", userInfoService.getUserById(id));
        return modelAndView;
    }

    @ResponseBody
    @PostMapping(value = "updateUer")
    public Resp updateUer(@RequestBody UserInfo userInfo) {
        userInfoService.updateUser(userInfo);
        return Resp.ok();
    }

    @ResponseBody
    @GetMapping(value = "deleteUser")
    public Resp deleteUser(@RequestParam(value = "id") long id) {
        userInfoService.deleteUser(id);
        return Resp.ok();
    }


    /**
     * 跳转新增用户界面
     *
     * @return
     */
    @GetMapping(value = "userAdd")
    public ModelAndView userAdd() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/userAdd");
        modelAndView.addObject("roleList", roleService.listRole());
        return modelAndView;
    }
}
