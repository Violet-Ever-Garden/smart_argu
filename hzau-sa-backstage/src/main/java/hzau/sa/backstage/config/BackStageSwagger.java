package hzau.sa.backstage.config;

import hzau.sa.msg.config.Swagger2Config;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author LvHao
 * @Description :
 * @date 2020-08-12 18:21
 */
@Configuration
public class BackStageSwagger extends Swagger2Config {

    @Bean
    public Docket backStageDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("HZAU-Smart-Agriculture 平台设置接口")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("hzau.sa.backstage"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build().securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

}
