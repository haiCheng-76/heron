package webiste.lhc.heron.controller;

import lombok.extern.slf4j.Slf4j;
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
    @GetMapping(value = "listMenus")
    public List<Menu> listMens() {
        List<Menu> menuList = menuService.getMenuByUserId(RoleConstant.ADMIN_USER_ID);
        log.info("data:[{}]", JsonUtil.toJsonString(menuList));
        return menuList;
    }

    @GetMapping(value = "menuPage")
    public ModelAndView listDir(@RequestParam(value = "parentId", required = false, defaultValue = "0") long pid,
                                @RequestParam(value = "type", required = false, defaultValue = "D") String type,
                                @RequestParam(value = "menuName", required = false, defaultValue = "Heron") String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("menu/listMenu");
        modelAndView.addObject("text", name);
        modelAndView.addObject("menuList", menuService.listMenuBYType(pid));
        modelAndView.addObject("menus", menuService.getMenuByUserId(getUerId()));
        return modelAndView;
    }


    /**
     * 跳转菜单添加界面
     *
     * @return
     */
    @GetMapping(value = "addMenu")
    public ModelAndView addMenu() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("menu/menuAdd");
        return modelAndView;
    }


    @GetMapping(value = "menuInfo")
    public ModelAndView menuInfo(@RequestParam(value = "parentId") long pid,
                                 @RequestParam(value = "type") String type) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("menu/menuInfo");
        modelAndView.addObject("menu", menuService.getMenuById(pid));
        modelAndView.addObject("pid", pid);
        return modelAndView;
    }

    @GetMapping(value = "menuUpdate")
    public ModelAndView menuUpdate(@RequestParam(value = "parentId") long pid,
                                   @RequestParam(value = "type") String type) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("menu/menuUpdate");
        modelAndView.addObject("menu", menuService.getMenuById(pid));
        modelAndView.addObject("pid", pid);
        return modelAndView;
    }

    /**
     * 通过ID删除菜单
     *
     * @param menuId
     * @return
     */
    @ResponseBody
    @GetMapping(value = "delMenu")
    public Resp delMenu(@RequestParam(value = "id") long menuId) {
        menuService.delMenuById(menuId);
        return Resp.ok();
    }

    @ResponseBody
    @PostMapping(value = "saveMenu")
    public Resp saveMenu(@RequestBody Menu menu) {
        log.info("menu:{}", menu.toString());
        menuService.insertMenu(menu);
        return Resp.ok();
    }


    @ResponseBody
    @GetMapping(value = "getTreeData")
    public List<Map<String, Object>>  getTreeData() {
        return menuService.listMenu();
    }

    @ResponseBody
    @PostMapping(value = "updateMenu")
    public Resp updateMenu(@RequestBody Menu menu) {
        log.info("updateMenu:{}", menu.toString());
        menuService.insertMenu(menu);
        return Resp.ok();
    }


    @ResponseBody
    @GetMapping(value = "getZtreeMenu")
    public List<ZtreeVo> getZtreeMenu() {
        return menuService.listDataToTree();
    }

}
