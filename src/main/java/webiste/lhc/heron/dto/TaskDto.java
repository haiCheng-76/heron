package webiste.lhc.heron.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TaskDto implements Serializable {

    private String taskName;
    private String description;
    private String cronExpression;
    private String beanClass;
    private Integer jobStatus;
    private String jobGroup;
}
