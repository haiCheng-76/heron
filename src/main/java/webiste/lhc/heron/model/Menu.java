package webiste.lhc.heron.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import webiste.lhc.heron.util.JsonUtil;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
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

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 类型；D:表示目录; M:表示菜单；B:表示按钮
     */
    private String type;

    /**
     * 目录对于的子菜单
     */
    private List<Menu> child;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return JsonUtil.toJsonString(this);
    }
            }
