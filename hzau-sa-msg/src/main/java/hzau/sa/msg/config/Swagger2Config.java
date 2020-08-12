package hzau.sa.msg.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author LvHao
 * @Description : swagger2配置的抽象类  需要被继承
 * @date 2020-08-09 16:37
 */
@Configuration
@EnableSwagger2
public abstract class Swagger2Config {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("HZAU-Smart-Agriculture日志信息接口")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("hzau.sa.msg"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build();
    }

    /**
     * 配置swagger2信息 apiInfo
     * @return
     */
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("接口管理平台")
                .description("接口管理及测试")
                .version("v1.0")
                .build();
    }

}
