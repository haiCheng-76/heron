package webiste.lhc.heron.service;

import webiste.lhc.heron.dto.MenuDto;
import webiste.lhc.heron.model.Menu;
import webiste.lhc.heron.vo.MenuVo;

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
    List<Menu> listMenu(String account);
}
