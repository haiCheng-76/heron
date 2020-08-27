package webiste.lhc.heron.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webiste.lhc.heron.mapper.MenuMapper;
import webiste.lhc.heron.model.Menu;
import webiste.lhc.heron.service.MenuService;

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

    @Autowired
    private MenuMapper menuMapper;

//    private

    @Override
    public List<Menu> listMenu(String account) {

        return null;
    }
}
