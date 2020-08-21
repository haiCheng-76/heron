package webiste.lhc.heron.mapper;

import webiste.lhc.heron.config.mapperconfig.BaseMapper;
import webiste.lhc.heron.model.Menu;
import webiste.lhc.heron.vo.MenuVo;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    List<MenuVo> listmenu(int parent, String type);
}
