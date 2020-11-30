package website.lhc.heron.service;

import website.lhc.heron.model.Menu;
import website.lhc.heron.vo.ZtreeVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

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


    List<Menu> listMenuBYType(long pid);


    Menu getMenuById(long id);

    void delMenuById(long id);

    List<ZtreeVo> listDataToTree(List<String> types);

    void insertMenu(Menu menu);

    void updateMenu(Menu menu);

    List<Map<String, Object>>  listMenu();

    Set<String> listPermissionByUserId(long userId);
}
