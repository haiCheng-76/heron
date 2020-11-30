package website.lhc.heron.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import website.lhc.heron.model.RoleInfo;
import website.lhc.heron.service.MenuService;
import website.lhc.heron.service.RoleService;
import website.lhc.heron.util.Resp;
import website.lhc.heron.vo.RoleVo;

/**
 * @description:
 * @author: 582895699@qq.com
 * @time: 2020/9/11 上午 12:01
 */
@Controller
@Api(tags = "角色管理")
@RequestMapping(value = "role")
public class RoleController extends AbstractController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "获取角色分页数据")
    @ResponseBody
    @GetMapping(value = "pageRole")
    public PageInfo<RoleInfo> pageRole(@RequestParam(value = "offset") int current,
                                       @RequestParam(value = "limit") int size) {
        return roleService.pageRole(current, size);
    }

    @ApiOperation(value = "跳转角色页面")
    @GetMapping(value = "rolePage")
    public ModelAndView rolePage(@RequestParam(value = "menuName", required = false, defaultValue = "Heron") String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("role/rolelist");
        modelAndView.addObject("text", name);
        modelAndView.addObject("menus", menuService.getMenuByUserId(getUerId()));
        return modelAndView;
    }

    @ApiOperation(value = "跳转角色添加页面")
    @GetMapping(value = "addRole")
    public String addRole() {
        return "role/addRole";
    }


    @ApiOperation(value = "保存角色信息")
    @ResponseBody
    @PostMapping(value = "saveRole")
    public Resp saveRole(@RequestBody RoleVo vo) {
        roleService.insertRole(vo);
        return Resp.ok();
    }

    @ApiOperation(value = "修改角色信息")
    @ResponseBody
    @PostMapping(value = "updateRole")
    public Resp updateRole(@RequestBody RoleVo vo) {
        roleService.updateRoleInfo(vo);
        return Resp.ok();
    }


    @ApiOperation(value = "删除角色")
    @ResponseBody
    @GetMapping(value = "delRole")
    public Resp delRole(@RequestParam(value = "id") long id) {
        roleService.deleteRole(id);
        return Resp.ok();
    }


    @ApiOperation(value = "获取角色详细信息")
    @ResponseBody
    @GetMapping(value = "getRole")
    public Resp getRole(@RequestParam(value = "id") long id) {
        RoleInfo roleInfo = roleService.getRoleInfoById(id);
        return Resp.ok(roleInfo);
    }

    @ApiOperation(value = "修改角色信息")
    @GetMapping(value = "updateRole")
    public ModelAndView updateRole(@RequestParam(value = "id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("role/updateRole");
        modelAndView.addObject("data", roleService.getRoleInfoById(id));
        return modelAndView;
    }

}
