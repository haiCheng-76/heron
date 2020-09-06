package webiste.lhc.heron.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import webiste.lhc.heron.commo.RoleConstant;
import webiste.lhc.heron.model.Menu;
import webiste.lhc.heron.service.MenuService;
import webiste.lhc.heron.util.JsonUtil;
import webiste.lhc.heron.util.Resp;
import webiste.lhc.heron.vo.ZtreeVo;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.controller
 * @ClassName: MenuController
 * @Author: lhc
 * @Description: TODO
 * @Date: 2020/8/16 下午 04:03
 */
@Slf4j
@Controller
@RequestMapping(value = "menu")
public class MenuController extends AbstractController {

    @Autowired
    private MenuService menuService;


    @ResponseBody
    @RequiresPermissions(value = {"sys:menu:view"})
    @GetMapping(value = "listMenus")
    public List<Menu> listMens() {
        List<Menu> menuList = menuService.getMenuByUserId(RoleConstant.ADMIN_USER_ID);
        log.info("data:[{}]", JsonUtil.toJsonString(menuList));
        return menuList;
    }

    @RequiresPermissions(value = "sys:menu:list")
    @GetMapping(value = "menuPage")
    public ModelAndView listDir(@RequestParam(value = "menuName", required = false, defaultValue = "Heron") String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("menu/listMenu");
        modelAndView.addObject("text", name);
        modelAndView.addObject("menus", menuService.getMenuByUserId(getUerId()));
        return modelAndView;
    }


    /**
     * 跳转菜单添加界面
     *
     * @return
     */
    @RequiresPermissions(value = "sys:menu:add")
    @GetMapping(value = "addMenu")
    public ModelAndView addMenu() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("menu/menuAdd");
        return modelAndView;
    }

//    @RequiresPermissions(value = "sys:menu:list")
//    @GetMapping(value = "menuInfo")
//    public ModelAndView menuInfo(@RequestParam(value = "parentId") long pid,
//                                 @RequestParam(value = "type") String type) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("menu/menuInfo");
//        modelAndView.addObject("menu", menuService.getMenuById(pid));
//        modelAndView.addObject("pid", pid);
//        return modelAndView;
//    }

    @RequiresPermissions(value = "sys:menu:alter")
    @GetMapping(value = "menuUpdate")
    public ModelAndView menuUpdate(@RequestParam(value = "parentId") long pid,
                                   @RequestParam(value = "id") long id,
                                   @RequestParam(value = "type") String type) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("menu/menuUpdate");
        modelAndView.addObject("menu", menuService.getMenuById(id));
        modelAndView.addObject("pid", pid);
        modelAndView.addObject("id", id);
        modelAndView.addObject("type", type);
        return modelAndView;
    }

    /**
     * 通过ID删除菜单
     *
     * @param menuId
     * @return
     */
    @RequiresPermissions(value = "sys:menu:del")
    @ResponseBody
    @GetMapping(value = "delMenu")
    public Resp delMenu(@RequestParam(value = "id") long menuId) {
        menuService.delMenuById(menuId);
        return Resp.ok();
    }

    @RequiresPermissions(value = "sys:menu:add")
    @ResponseBody
    @PostMapping(value = "saveMenu")
    public Resp saveMenu(@RequestBody Menu menu) {
        log.info("menu:{}", menu.toString());
        menuService.insertMenu(menu);
        return Resp.ok();
    }


    @RequiresPermissions(value = "sys:menu:list")
    @ResponseBody
    @GetMapping(value = "getTreeData")
    public List<Map<String, Object>> getTreeData() {
        return menuService.listMenu();
    }



    @RequiresPermissions(value = "sys:menu:alter")
    @ResponseBody
    @PostMapping(value = "updateMenu")
    public Resp updateMenu(@RequestBody Menu menu) {
        log.info("updateMenu:{}", menu.toString());
        menuService.updateMenu(menu);
        return Resp.ok();
    }


    @RequiresPermissions(value = "sys:menu:list")
    @ResponseBody
    @GetMapping(value = "getZtreeMenu")
    public List<ZtreeVo> getZtreeMenu() {
        return menuService.listDataToTree();
    }

}
