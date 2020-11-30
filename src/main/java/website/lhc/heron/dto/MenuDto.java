package website.lhc.heron.dto;

import java.io.Serializable;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.dto
 * @ClassName: MenuDto
 * @Author: lhc
 * @Description: TODO
 * @Date: 2020/8/16 下午 08:29
 */
public class MenuDto implements Serializable {
    private static final long serialVersionUID = -6071054323755918942L;

    private String menuName;
    private String url;
    private Integer sort;
    private String icon;
    private String type;
    private Long parentId;
    private String permission;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
