package webiste.lhc.heron.vo;

import java.io.Serializable;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.vo
 * @ClassName: MenuForTree
 * @Author: lhc
 * @Description: TODO
 * @Date: 2020/8/16 下午 07:39
 */
public class MenuForTree implements Serializable {
    private static final long serialVersionUID = 1583233230504784331L;
    private Long id;
    private Long pId;
    private String name;

    public MenuForTree() {
    }

    public MenuForTree(Long id, Long pId, String name) {
        this.id = id;
        this.pId = pId;
        this.name = name;
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
}
