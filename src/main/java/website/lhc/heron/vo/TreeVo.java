package website.lhc.heron.vo;

import java.io.Serializable;

/**
 * @author haicheng.long@androidmov.com
 * @ClassName ZtreeVo
 * @Description TODO
 * @date 2020/9/1 下午 3:01
 */
public class TreeVo implements Serializable {
    private static final long serialVersionUID = 7604374064961645507L;
    private Long id;
    private Long pid;
    private String title;

    public TreeVo() {
    }

    public TreeVo(Long id, Long pid, String title) {
        this.id = id;
        this.pid = pid;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
