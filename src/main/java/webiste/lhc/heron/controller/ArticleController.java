package webiste.lhc.heron.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import webiste.lhc.heron.service.MenuService;

@Controller
@RequestMapping(value = "article")
public class ArticleController extends AbstractController {

    @Autowired
    private MenuService menuService;

    @RequiresPermissions(value = "sys:article:add")
    @GetMapping(value = "/articlePage")
    public ModelAndView articlePage(@RequestParam(value = "menuName", required = false, defaultValue = "Heron") String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("article/addArticle");
        modelAndView.addObject("text", name);
        modelAndView.addObject("menus", menuService.getMenuByUserId(getUerId()));
        return modelAndView;
    }
}
