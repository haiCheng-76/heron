package website.lhc.heron.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import website.lhc.heron.model.Menu;
import website.lhc.heron.service.MenuService;
import website.lhc.heron.util.Resp;
import website.lhc.heron.vo.ZtreeVo;

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
@Api(tags = "菜单管理")
@Controller
@RequestMapping(value = "menu")
public class MenuController extends AbstractController {

    @Autowired
    private MenuService menuService;

    /**
     * @param name
     * @return
     */
    @ApiOperation(value = "跳转菜单管理界面")
    @RequiresPermissions(value = "sys:menu:view")
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
    @ApiOperation(value = "跳转菜单添加界面")
    @RequiresPermissions(value = "sys:menu:add")
    @GetMapping(value = "addMenu")
    public ModelAndView addMenu() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("menu/menuAdd");
        return modelAndView;
    }

    /**
     * 跳转菜单编辑界面
     *
     * @param pid
     * @param id
     * @param type
     * @return
     */
    @ApiOperation(value = "跳转菜单编辑界面")
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
     * 删除菜单
     *
     * @param menuId
     * @return
     */
    @ApiOperation(value = "删除菜单")
    @RequiresPermissions(value = "sys:menu:del")
    @ResponseBody
    @GetMapping(value = "delMenu")
    public Resp delMenu(@RequestParam(value = "id") long menuId) {
        menuService.delMenuById(menuId);
        return Resp.ok();
    }

    /**
     * 新增菜单
     *
     * @param menu
     * @return
     */
    @ApiOperation(value = "新增菜单")
    @RequiresPermissions(value = "sys:menu:add")
    @ResponseBody
    @PostMapping(value = "saveMenu")
    public Resp saveMenu(@RequestBody Menu menu) {
        log.info("menu:{}", menu.toString());
        menuService.insertMenu(menu);
        return Resp.ok();
    }


    /**
     * layui-treeTable树形菜单
     *
     * @return List<Map < String, Object>>
     */
    @ApiOperation(value = "获取Tree形菜单")
    @RequiresPermissions(value = "sys:menu:view")
    @ResponseBody
    @GetMapping(value = "getTreeTable")
    public List<Map<String, Object>> getTreeTable() {
        return menuService.listMenu();
    }


    /**
     * 编辑菜单
     *
     * @param menu
     * @return
     */
    @ApiOperation(value = "编辑菜单")
    @RequiresPermissions(value = "sys:menu:alter")
    @ResponseBody
    @PostMapping(value = "updateMenu")
    public Resp updateMenu(@RequestBody Menu menu) {
        log.info("updateMenu:{}", menu.toString());
        menuService.updateMenu(menu);
        return Resp.ok();
    }

    /**
     * ztree树形菜单
     *
     * @param strings
     * @return
     */
    @ApiOperation(value = "获取Ztree菜单")
    @ResponseBody
    @PostMapping(value = "getZtreeMenu")
    public List<ZtreeVo> getZtreeMenu(@RequestBody List<String> strings) {
        log.info("strings:{}", strings);
        return menuService.listDataToTree(strings);
    }

}
