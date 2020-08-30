package webiste.lhc.heron.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import webiste.lhc.heron.service.MenuService;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.controller
 * @ClassName: MenuController
 * @Author: lhc
 * @Description: TODO
 * @Date: 2020/8/16 下午 04:03
 */
@Controller
@RequestMapping(value = "menu")
public class MenuController extends AbstractController {

    @Autowired
    private MenuService menuService;

    @GetMapping(value = "menuPage")
    public String menuPage() {
        return "menu/listMenu";
    }
}
