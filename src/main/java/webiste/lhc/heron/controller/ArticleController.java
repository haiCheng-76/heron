package webiste.lhc.heron.controller;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;
import webiste.lhc.heron.dto.ArticleDto;
import webiste.lhc.heron.model.Article;
import webiste.lhc.heron.service.ArticleService;
import webiste.lhc.heron.service.MenuService;
import webiste.lhc.heron.util.Resp;

import java.util.Map;

@Slf4j
@Controller
@RequestMapping(value = "article")
public class ArticleController extends AbstractController {

    @Autowired
    private ArticleService articleService;

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


    @RequiresPermissions(value = "sys:article:view")
    @GetMapping(value = "/articleListPage")
    public ModelAndView articleListPage(@RequestParam(value = "menuName", required = false, defaultValue = "Heron") String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("article/articleList");
        modelAndView.addObject("text", name);
        modelAndView.addObject("menus", menuService.getMenuByUserId(getUerId()));
        return modelAndView;
    }


    @ResponseBody
    @GetMapping(value = "pageArticle")
    @RequiresPermissions(value = "sys:article:list")
    public PageInfo<Article> pageArticle(@RequestParam(value = "current", defaultValue = "0") int current,
                                         @RequestParam(value = "size", defaultValue = "10") int size) {
        return articleService.pageArticle(current, size);

    }


    @RequiresPermissions(value = "sys:article:view")
    @GetMapping(value = "/articleDetail")
    public ModelAndView articleDetail(@RequestParam(value = "articleName") String name, @RequestParam(value = "id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("article/articleDetail");
        modelAndView.addObject("text", name);
        modelAndView.addObject("menus", menuService.getMenuByUserId(getUerId()));
        Map<String, String> map = articleService.getContentById(id);
        modelAndView.addObject("name", map.get("name"));
        modelAndView.addObject("content", map.get("content"));
        return modelAndView;
    }


    /**
     * 添加文章
     *
     * @param dto
     * @return
     */
    @RequiresPermissions(value = {"sys:article:add"}, logical = Logical.OR)
    @ResponseBody
    @PostMapping(value = "saveArticle")
    public Resp saveArticle(@RequestBody ArticleDto dto) {
        articleService.addArticle(dto);
        return Resp.ok();
    }


    @RequiresPermissions(value = {"sys:article:del"})
    @ResponseBody
    @GetMapping(value = "delArticle")
    public Resp delArticle(@RequestParam(value = "id") long id) {
        articleService.removeArticle(id);
        return Resp.ok();
    }


    /**
     * 跳转文章编辑界面
     *
     * @param name
     * @return
     */
    @RequiresPermissions(value = "sys:article:alter")
    @GetMapping(value = "/updateDetail")
    public ModelAndView updateDetail(@RequestParam(value = "menuName", required = false, defaultValue = "Heron") String name, @RequestParam(value = "id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("article/articleUpdate");
        modelAndView.addObject("text", name);
        modelAndView.addObject("menus", menuService.getMenuByUserId(getUerId()));
        Article article = articleService.getArticleById(id);
        modelAndView.addObject("title", article.getArticleName());
        modelAndView.addObject("content", article.getContent());
        modelAndView.addObject("id", id);
        return modelAndView;
    }


    @RequiresPermissions(value = {"sys:article:alter"})
    @ResponseBody
    @PostMapping(value = "updateArticle")
    public Resp updateArticle(@RequestBody ArticleDto dto) {
        articleService.updateArticle(dto);
        return Resp.ok();
    }


}
