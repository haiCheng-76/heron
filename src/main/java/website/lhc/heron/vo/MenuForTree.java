package website.lhc.heron.vo;

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
    private String text;
    private String url;

    public MenuForTree(Long id, Long pId, String text, String url) {
        this.id = id;
        this.pId = pId;
        this.text = text;
        this.url = url;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
