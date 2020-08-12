package hzau.sa.test.config;

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
 * @date 2020-08-09 17:45
 */
@Configuration
public class TestSwagger2Config extends Swagger2Config {

    @Bean
    public Docket testDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("HZAU-Smart-Agriculture测试接口")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("hzau.sa.test"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build();
    }

}
