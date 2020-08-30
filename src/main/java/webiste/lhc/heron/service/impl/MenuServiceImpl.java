package webiste.lhc.heron.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import webiste.lhc.heron.mapper.MenuMapper;
import webiste.lhc.heron.model.Menu;
import webiste.lhc.heron.service.MenuService;

import java.util.ArrayList;
import java.util.List;

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


    /**
     * 超级管理员ID
     */
    private static final Long ADMIN_USER_ID = 1L;

    @Autowired
    private MenuMapper menuMapper;


    /**
     * 先获取目录，在获取菜单
     *
     * @param userId
     * @return
     */
    @Override
    public List<Menu> getMenuByUserId(long userId) {
        // 所有菜单
        if (ADMIN_USER_ID == userId) {
            return getAllMenus(null);
        }
        List<Long> menuIdList = menuMapper.listMenuIdByUserId(userId);
        return getAllMenus(menuIdList);
    }


    /**
     * 若menuIdList为空表示超级管理员，否则表示特定用户
     *
     * @param menuIdList
     * @return
     */
    private List<Menu> listMenu(Long parentId, List<Long> menuIdList) {
        List<Menu> dirList = listParent(parentId);
        if (CollectionUtils.isEmpty(menuIdList)) {
            return dirList;
        }
        List<Menu> menus = new ArrayList<>();
        for (Menu menu : dirList) {
            if (menuIdList.contains(menu.getId())) {
                menus.add(menu);
            }
        }
        return menus;
    }

    public List<Menu> getAllMenus(List<Long> idList) {
        List<Menu> parents = listMenu(0L, idList);
        listMenus(parents, idList);
        return parents;
    }

    /**
     * 获取所有parentId的子节点
     *
     * @param parentId
     * @return
     */
    private List<Menu> listParent(long parentId) {
        return menuMapper.getParent(parentId);
    }

    private List<Menu> listMenus(List<Menu> dirList, List<Long> menuIdList) {
        List<Menu> subMenuList = new ArrayList<>();
        for (Menu menu : dirList) {
            if ("D".equals(menu.getType())) {
                menu.setChild(listMenus(listMenu(menu.getId(), menuIdList), menuIdList));
            }
            subMenuList.add(menu);
        }
        return subMenuList;
    }

}
