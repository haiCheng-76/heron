package webiste.lhc.heron.mapper;

import org.apache.ibatis.annotations.Param;
import webiste.lhc.heron.config.mapperconfig.BaseMapper;
import webiste.lhc.heron.model.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> getParent(long id);

    /**
     * userId对应的目录，菜单和按钮ID
     *
     * @param userId
     * @return
     */
    List<Long> listMenuIdByUserId(long userId);


    List<Menu> listAvaliableMenu(long id);

    List<Menu> listTree(@Param(value = "id") long id, @Param(value = "type") String type);

    int menuCount(long id);
}
