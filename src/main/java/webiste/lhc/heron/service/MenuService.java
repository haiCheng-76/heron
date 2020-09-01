package webiste.lhc.heron.service;

import webiste.lhc.heron.model.Menu;

import java.util.List;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.service
 * @ClassName: MenuService
 * @Author: lhc
 * @Description: TODO
 * @Date: 2020/8/16 下午 05:23
 */
public interface MenuService {

    List<Menu> getMenuByUserId(long userId);
    List<Menu> listMenu();
    List<Menu> listMenuBYType(long pid, String type);


    Menu getMenuById(long id);

    void delMenuById(long id);
}
