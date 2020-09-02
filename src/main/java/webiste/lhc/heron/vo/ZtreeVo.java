package webiste.lhc.heron.vo;

import java.io.Serializable;

/**
 * @author haicheng.long@androidmov.com
 * @ClassName ZtreeVo
 * @Description TODO
 * @date 2020/9/1 下午 3:01
 */
public class ZtreeVo implements Serializable {
    private Long id;
    private Long pId;
    private String name;
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
