package website.lhc.heron.vo;

import java.io.Serializable;

/**
 * @description: ztree返回类 用于树形显示菜单信息
 * @author: 582895699@qq.com
 * @time: 2020/9/3 下午 11:56
 */
public class ZtreeVo implements Serializable {

    private static final long serialVersionUID = 1189444780140043125L;
    /**
     * id
     */
    private Long id;
    /**
     * 父id
     */
    private Long pId;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 是否有子元素
     */
    private Boolean isParent;

    public ZtreeVo() {
    }

    public ZtreeVo(Long id, Long pId, String name, Boolean isParent) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.isParent = isParent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getParent() {
        return isParent;
    }

    public void setParent(Boolean parent) {
        isParent = parent;
    }
}
