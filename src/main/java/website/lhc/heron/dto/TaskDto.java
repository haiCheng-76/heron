package website.lhc.heron.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class TaskDto implements Serializable {
    private Long id;
    private String taskName;

    @NotBlank(message = "[description]不可为空")
    private String description;

    @NotBlank(message = "[cronExpression]不可为空")
    private String cronExpression;

    @NotBlank(message = "[beanClass]不可为空")
    private String beanClass;

    @NotNull(message = "[jobStatus]不可为空")
    private Integer jobStatus;
    private String jobGroup;
}
