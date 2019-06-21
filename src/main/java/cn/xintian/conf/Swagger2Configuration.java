package cn.xintian.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 主要作用是生成 API 接口文档说明
 */
@Configuration //指定这是一个配置类
@EnableSwagger2 //指定 Swagger
public class Swagger2Configuration {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select() // 选择那些路径和 api 会生成 document
                //指定要扫描的包(会扫描所有模块下的 cn.xintian.controller 里的带有 restcontroller注解的类)
                .apis(RequestHandlerSelectors.basePackage("cn.xintian.controller"))
                .paths(PathSelectors.any()) //对所有路径进行监控
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("微信模板开发")
                .description("模板开发 api 文档")
                // .termsOfServiceUrl("/")
                .version("1.0")
                .build();
    }
}
