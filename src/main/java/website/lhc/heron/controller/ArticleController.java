package website.lhc.heron.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import website.lhc.heron.dto.ArticleDto;
import website.lhc.heron.model.Article;
import website.lhc.heron.service.ArticleService;
import website.lhc.heron.service.MenuService;
import website.lhc.heron.util.Resp;

import java.util.Map;

@Slf4j
@Api(tags = "文章管理")
@Controller
@RequestMapping(value = "article")
public class ArticleController extends AbstractController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "跳转文章新增界面")
    @RequiresPermissions(value = "sys:article:add")
    @GetMapping(value = "/articlePage")
    public ModelAndView articlePage(@RequestParam(value = "menuName", required = false, defaultValue = "Heron") String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("article/addArticle");
        modelAndView.addObject("text", name);
        modelAndView.addObject("menus", menuService.getMenuByUserId(getUerId()));
        return modelAndView;
    }


    @ApiOperation(value = "跳转文章页面")
    @RequiresPermissions(value = "sys:article:view")
    @GetMapping(value = "/articleListPage")
    public ModelAndView articleListPage(@RequestParam(value = "menuName", required = false, defaultValue = "Heron") String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("article/articleList");
        modelAndView.addObject("text", name);
        modelAndView.addObject("menus", menuService.getMenuByUserId(getUerId()));
        return modelAndView;
    }


    @ApiOperation(value = "获取文章分页数据")
    @ResponseBody
    @GetMapping(value = "pageArticle")
    @RequiresPermissions(value = "sys:article:list")
    public PageInfo<Article> pageArticle(@RequestParam(value = "offset") int current,
                                         @RequestParam(value = "limit") int size) {
        return articleService.pageArticle(current, size);

    }

    @ApiOperation(value = "文章详细")
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
    @ApiOperation(value = "添加文章")
    @RequiresPermissions(value = {"sys:article:add"}, logical = Logical.OR)
    @ResponseBody
    @PostMapping(value = "saveArticle")
    public Resp saveArticle(@RequestBody ArticleDto dto) {
        articleService.addArticle(dto);
        return Resp.ok();
    }

    @ApiOperation(value = "删除文章")
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
    @ApiOperation(value = "跳转文章编辑界面")
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


    @ApiOperation(value = "编辑文章")
    @RequiresPermissions(value = {"sys:article:alter"})
    @ResponseBody
    @PostMapping(value = "updateArticle")
    public Resp updateArticle(@RequestBody ArticleDto dto) {
        articleService.updateArticle(dto);
        return Resp.ok();
    }


}
