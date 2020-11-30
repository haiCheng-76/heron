package website.lhc.heron.framework.knife4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @description:
 * @author: 582895699@qq.com
 * @time: 2020/11/30 下午 09:24
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    @Bean()
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("Heron")
                        .description("基于Java的网站内容管理系统")
                        .termsOfServiceUrl("https://haicheng.website")
                        .contact(new Contact("龙海成", "https://haicheng.website", "582895699@qq.com"))
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("1.0")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("website.lhc.heron.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
