package website.lhc.heron.mapper;

import org.apache.ibatis.annotations.Param;
import website.lhc.heron.framework.mybatis.BaseMapper;
import website.lhc.heron.model.Menu;
import website.lhc.heron.vo.ZtreeVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    List<Map<String, Object>> listMenus();


    List<ZtreeVo> getMenu(@Param(value = "types") List<String> types);

    int getCount(@Param(value = "id") long id);

    /**
     * 用户Id查询所属角色
     * @param userId
     * @return
     */
    Set<String> getPermissionByUserId(long userId);
}
