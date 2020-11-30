package website.lhc.heron.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author: 582895699@qq.com
 * @time: 2020/9/26 下午 02:30
 */
@Getter
@Setter
public class UserVo implements Serializable {
    private static final long serialVersionUID = -7115072578071488767L;

    @NotBlank(message = "用户名不为空")
    private String userName;

    @NotBlank(message = "账户名不为空")
    private String account;

    @NotBlank(message = "密码不为空")
    private String password;

    @Email(message = "请输入正确的邮箱地址")
    @NotBlank(message = "邮箱不为空")
    private String mail;

    @NotNull(message = "角色不可为空")
    private Long roleId;
}
