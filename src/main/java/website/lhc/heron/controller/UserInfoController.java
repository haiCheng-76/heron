package website.lhc.heron.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import website.lhc.heron.dto.UserVo;
import website.lhc.heron.model.UserInfo;
import website.lhc.heron.service.MenuService;
import website.lhc.heron.service.RoleService;
import website.lhc.heron.service.UserInfoService;
import website.lhc.heron.util.Resp;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.controller
 * @ClassName: UserInfoController
 * @Author: lhc
 * @Description: 用户相关
 * @Date: 2020/8/16 下午 02:35
 */
@Slf4j
@Api(tags = "用户管理")
@Controller
@RequestMapping(value = "userInfo")
public class UserInfoController extends AbstractController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "添加用户信息")
    @ResponseBody
    @PostMapping(value = "addUserInfo")
    public Resp addUserInfo(@RequestBody UserVo vo) {
        userInfoService.insertUserInfo(vo);
        return Resp.ok();
    }


    @ApiOperation(value = "跳转用户页面")
    @GetMapping(value = "/viewUsers")
    public ModelAndView viewUsers(@RequestParam(value = "menuName", required = false, defaultValue = "Heron") String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/userList");
        modelAndView.addObject("text", name);
        modelAndView.addObject("menus", menuService.getMenuByUserId(getUerId()));
        return modelAndView;
    }

    @ApiOperation(value = "获取用户分页数据")
    @ResponseBody
    @GetMapping(value = "pageUserInfo")
    public PageInfo<UserInfo> pageUserInfo(@RequestParam(value = "offset") int current,
                                           @RequestParam(value = "limit") int size) {
        PageInfo<UserInfo> pageInfo = userInfoService.pageUserInfo(current, size);
        return pageInfo;
    }

    @ApiOperation(value = "获取用户详细信息")
    @GetMapping(value = "getUserInfo")
    public ModelAndView getUserInfo(@RequestParam(value = "id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/userDetail");
        modelAndView.addObject("user", userInfoService.getUserById(id));
        return modelAndView;
    }


    @ApiOperation(value = "跳转用户信息修改页面")
    @GetMapping(value = "updateUserInfo")
    public ModelAndView updateUserInfo(@RequestParam(value = "id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/updateUser");
        modelAndView.addObject("user", userInfoService.getUserById(id));
        return modelAndView;
    }


    @ApiOperation(value = "修改用户信息")
    @ResponseBody
    @PostMapping(value = "updateUer")
    public Resp updateUer(@RequestBody UserInfo userInfo) {
        userInfoService.updateUser(userInfo);
        return Resp.ok();
    }


    @ApiOperation(value = "删除用户")
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
    @ApiOperation(value = "跳转新增用户界面")
    @GetMapping(value = "userAdd")
    public ModelAndView userAdd() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/userAdd");
        modelAndView.addObject("roleList", roleService.listRole());
        return modelAndView;
    }
}
