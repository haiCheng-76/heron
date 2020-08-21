package webiste.lhc.heron.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import webiste.lhc.heron.dto.MenuDto;
import webiste.lhc.heron.mapper.MenuMapper;
import webiste.lhc.heron.model.Menu;
import webiste.lhc.heron.service.MenuService;
import webiste.lhc.heron.vo.MenuVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.service.impl
 * @ClassName: MenuServiceImpl
 * @Author: lhc
 * @Description: TODO
 * @Date: 2020/8/16 下午 05:24
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> listParentMen() {
        Menu menu = new Menu();
        menu.setParentId(0L);
        List<Menu> parentMenus = menuMapper.select(menu);
        listChildMenu(parentMenus);
        return parentMenus;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertMenu(MenuDto menuDto) {
        Menu menu = new Menu();
        menu.setParentId(menuDto.getParentId());
        menu.setMenuName(menuDto.getMenuName());
        menu.setUrl(menuDto.getUrl());
        menu.setCreateTime(new Date());
        menu.setIcon(menuDto.getIcon());
        menu.setSort(menuDto.getSort());
        menu.setPermission(menuDto.getPermission());
        menu.setType(menuDto.getType());
        menuMapper.insertSelective(menu);
    }

    @Override
    public List<MenuVo> listMenus() {
//        Menu menu = new Menu();
//        menu.setParentId(0L);
        List<MenuVo> parentMenus = menuMapper.listmenu(0,"M");
        getChild(parentMenus);
        return parentMenus;
    }

    private List<MenuVo> getChild(List<MenuVo> menus) {
//        for (Menu item : menus) {
//            Menu menu = new Menu();
//            menu.setIsDelete(false);
//            menu.setParentId(item.getId());
//            menu.setType("M");
////            List<Menu> menuList = ;
//            item.setChild(getChild(menuMapper.select(menu)));
//        }
//        for (Map<String, Object> menu : menus) {
////            List<Map<String,Object>> parentMenus = menuMapper.listmenu((Long) menu.get("id"),"M");
//            menu.put("children", getChild(menuMapper.listmenu((Integer) menu.get("id"),"M")));
//        }
        for (MenuVo menu : menus) {
            menu.setChildren(getChild(menuMapper.listmenu(menu.getId(), "M")));
        }
        return menus;
    }

    private List<Menu> listChildMenu(List<Menu> parentMenus) {
        if (!CollectionUtils.isEmpty(parentMenus)) {
            List<Menu> list = new ArrayList<>();
            for (Menu parentMenu : parentMenus) {
                Menu menu = new Menu();
                menu.setParentId(parentMenu.getId());
                menu.setType("M");
                List<Menu> menus = menuMapper.select(menu);
                list.addAll(menus);
                listChildMenu(menus);
            }
            parentMenus.addAll(list);
        }
        return parentMenus;
    }
}
