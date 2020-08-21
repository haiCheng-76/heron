package webiste.lhc.heron.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import webiste.lhc.heron.commo.enums.MenuEnum;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Menu implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 父菜单Id
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 菜单名称
     */
    @Column(name = "menu_name")
    private String menuName;

    /**
     * 菜单对应的地址
     */
    private String url;

    /**
     * 权限
     */
    private String permission;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 1:表示删除；0:表示未删除
     */
    @Column(name = "is_delete")
    private Boolean isDelete;

    /**
     * 菜单图标
     */
    private String icon;

    private Integer sort;

    @Column(name = "type")
    private String type;


    private List<Menu> child;

    private static final long serialVersionUID = 1L;




    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取父菜单Id
     *
     * @return parent_id - 父菜单Id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父菜单Id
     *
     * @param parentId 父菜单Id
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取菜单名称
     *
     * @return menu_name - 菜单名称
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * 设置菜单名称
     *
     * @param menuName 菜单名称
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    /**
     * 获取菜单对应的地址
     *
     * @return url - 菜单对应的地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置菜单对应的地址
     *
     * @param url 菜单对应的地址
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取权限
     *
     * @return permission - 权限
     */
    public String getPermission() {
        return permission;
    }

    /**
     * 设置权限
     *
     * @param permission 权限
     */
    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取1:表示删除；0:表示未删除
     *
     * @return is_delete - 1:表示删除；0:表示未删除
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * 设置1:表示删除；0:表示未删除
     *
     * @param isDelete 1:表示删除；0:表示未删除
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 获取菜单图标
     *
     * @return icon - 菜单图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置菜单图标
     *
     * @param icon 菜单图标
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Menu> getChild() {
        return child;
    }

    public void setChild(List<Menu> child) {
        this.child = child;
    }
}
