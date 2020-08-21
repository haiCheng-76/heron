package webiste.lhc.heron.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import webiste.lhc.heron.dto.MenuDto;
import webiste.lhc.heron.model.Menu;
import webiste.lhc.heron.service.MenuService;
import webiste.lhc.heron.util.Resp;
import webiste.lhc.heron.vo.MenuForTree;
import webiste.lhc.heron.vo.MenuVo;

import java.util.ArrayList;
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
@Controller
@RequestMapping(value = "menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping(value = "addMenu")
    public String menu() {
        return "addMenu";
    }

    @GetMapping(value = "list")
    public String list() {
        return "menu/listMenu";
    }

    @ResponseBody
    @PostMapping(value = "addMenu")
    public Resp addMenu(@RequestBody MenuDto dto) {
        menuService.insertMenu(dto);
        return Resp.ok();
    }

    @ResponseBody
    @GetMapping(value = "getMenuForTree")
    public Resp getParentMenu() {
        List<Menu> list = menuService.listParentMen();
        List<MenuForTree> ztreeVo = new ArrayList<>(list.size());
        list.forEach(item -> {
            MenuForTree tree = new MenuForTree();
            tree.setId(item.getId());
            tree.setpId(item.getParentId());
            tree.setName(item.getMenuName());
            ztreeVo.add(tree);
        });
        ztreeVo.add(new MenuForTree(0L, -1L, "根节点"));
        return Resp.ok(ztreeVo);
    }

    @ResponseBody
    @GetMapping(value = "listMenus")
    public Resp listMenus() {
        List<MenuVo> list = menuService.listMenus();
        return Resp.ok(list);
    }
}
