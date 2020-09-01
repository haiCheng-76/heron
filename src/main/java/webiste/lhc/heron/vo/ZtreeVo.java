package webiste.lhc.heron.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author haicheng.long@androidmov.com
 * @ClassName ZtreeVo
 * @Description TODO
 * @date 2020/9/1 下午 3:01
 */
@Getter
@Setter
public class ZtreeVo implements Serializable {
    private Long id;
    private Long pid;
    private String name;
}
