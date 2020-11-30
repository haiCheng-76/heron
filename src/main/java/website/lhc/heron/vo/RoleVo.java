package website.lhc.heron.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: 582895699@qq.com
 * @time: 2020/9/25 上午 12:09
 */
@Getter
@Setter
public class RoleVo implements Serializable {

    private static final long serialVersionUID = -4316949128088664090L;
    private Long id;
    private String roleName;
    private Integer sort;
    private String remark;
    private List<Integer> ids;
}
