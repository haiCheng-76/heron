package webiste.lhc.heron.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import webiste.lhc.heron.model.RoleInfo;
import webiste.lhc.heron.service.MenuService;
import webiste.lhc.heron.service.RoleService;

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

}
