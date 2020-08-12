package hzau.sa.security.swagger;

import hzau.sa.msg.config.Swagger2Config;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
public class UserSwaggerConfig extends Swagger2Config {
    @Bean
    public Docket testDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("HZAU-Smart-Agriculture Shiro 接口")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("hzau.sa.security"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build()
                .securitySchemes(securitySchemes());
    }
    public List<ApiKey> securitySchemes() {
        return newArrayList(new ApiKey("Authorization", "token", "header"));
    }
}
