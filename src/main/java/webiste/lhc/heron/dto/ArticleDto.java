package webiste.lhc.heron.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class ArticleDto implements Serializable {


    private Long id;
    @NotBlank(message = "标题不可为空")
    private String title;
    @NotBlank(message = "内容不可为空")
    private String content;
}
