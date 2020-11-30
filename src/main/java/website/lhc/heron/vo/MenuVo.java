package website.lhc.heron.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @ProjectName: heron
 * @Package: webiste.lhc.heron.vo
 * @ClassName: MenuVo
 * @Author: lhc
 * @Description: TODO
 * @Date: 2020/8/16 下午 11:10
 */
public class MenuVo implements Serializable {
    private static final long serialVersionUID = -382330665851925725L;
    private Integer id;
    private String text;
    private String url;
    private String icon;

    private List<MenuVo> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<MenuVo> getChildren() {
        return children;
    }

    public void setChildren(List<MenuVo> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "MenuVo{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", children=" + children +
                '}';
    }
}
