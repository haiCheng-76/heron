package webiste.lhc.heron.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import webiste.lhc.heron.model.RoleInfo;
import webiste.lhc.heron.service.MenuService;
import webiste.lhc.heron.service.RoleService;
import webiste.lhc.heron.util.Resp;
import webiste.lhc.heron.vo.RoleVo;

/**
 * @description:
 * @author: 582895699@qq.com
 * @time: 2020/9/11 上午 12:01
 */
@Controller
@RequestMapping(value = "role")
public class RoleController extends AbstractController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @ResponseBody
    @GetMapping(value = "pageRole")
    public PageInfo<RoleInfo> pageRole(@RequestParam(value = "current", defaultValue = "0") int current,
                                       @RequestParam(value = "size", defaultValue = "10") int size) {
        return roleService.pageRole(current, size);
    }

    @GetMapping(value = "rolePage")
    public ModelAndView rolePage(@RequestParam(value = "menuName", required = false, defaultValue = "Heron") String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("role/rolelist");
        modelAndView.addObject("text", name);
        modelAndView.addObject("menus", menuService.getMenuByUserId(getUerId()));
        return modelAndView;
    }

    @GetMapping(value = "addRole")
    public String addRole() {
        return "role/addRole";
    }

    @ResponseBody
    @PostMapping(value = "saveRole")
    public Resp saveRole(@RequestBody RoleVo vo) {
        roleService.insertRole(vo);
        return Resp.ok();
    }

    @ResponseBody
    @GetMapping(value = "delRole")
    public Resp delRole(@RequestParam(value = "id") long id) {
        roleService.deleteRole(id);
        return Resp.ok();
    }

    @ResponseBody
    @GetMapping(value = "getRole")
    public Resp getRole(@RequestParam(value = "id") long id) {
        RoleInfo roleInfo = roleService.getRoleInfoById(id);
        return Resp.ok(roleInfo);
    }

    @GetMapping(value = "updateRole")
    public ModelAndView updateRole(@RequestParam(value = "id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("role/updateRole");
        modelAndView.addObject("data", roleService.getRoleInfoById(id));
        return modelAndView;
    }

}
