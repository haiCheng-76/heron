package webiste.lhc.heron.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import webiste.lhc.heron.commo.RoleConstant;
import webiste.lhc.heron.commo.enums.MenuEnum;
import webiste.lhc.heron.model.Menu;
import webiste.lhc.heron.service.MenuService;
import webiste.lhc.heron.util.JsonUtil;

import java.util.List;

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

//    @GetMapping(value = "menuPage")
//    public ModelAndView menuPage(@RequestParam(value = "menuName", required = false, defaultValue = "Heron") String name) {
//        log.info("menuName:{}", name);
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("menu/listMenu");
//        modelAndView.addObject("text", name);
//        return modelAndView;
//    }

    @ResponseBody
    @GetMapping(value = "listMenus")
    public List<Menu> listMens() {
        List<Menu> menuList = menuService.getMenuByUserId(RoleConstant.ADMIN_USER_ID);
        log.info("data:[{}]", JsonUtil.toJsonString(menuList));
        return menuList;
//        return Resp.ok(menuList);
    }


    @ResponseBody
    @GetMapping(value = "menuToTable")
    public List<Menu> menuToTable() {
        List<Menu> list = menuService.listMenu();
        list.forEach(item -> {
            if (item.getType().equals(MenuEnum.DIR.val())) {
                item.setId(null);
            }
        });
        return list;
    }

    @GetMapping(value = "menuPage")
    public ModelAndView listDir(@RequestParam(value = "parentId",required = false, defaultValue = "0") long pid,
                                @RequestParam(value = "type", required = false, defaultValue = "D") String type,
                                @RequestParam(value = "menuName", required = false, defaultValue = "Heron") String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("menu/listMenu");
        modelAndView.addObject("text", name);
        modelAndView.addObject("menus", menuService.listMenuBYType(pid, type));
        return modelAndView;
    }



    @GetMapping(value = "menuInfo")
    public ModelAndView menuInfo(@RequestParam(value = "parentId") long pid,
                                @RequestParam(value = "type") String type) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("menu/menuInfo");
        modelAndView.addObject("menus", menuService.listMenuBYType(pid, type));
        return modelAndView;
    }

    @GetMapping(value = "menuUpdate")
    public ModelAndView menuUpdate(@RequestParam(value = "parentId") long pid,
                                  @RequestParam(value = "type") String type) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("menu/menuUpdate");
        modelAndView.addObject("menus", menuService.listMenuBYType(pid, type));
        return modelAndView;
    }


}
