package hzau.sa.sensorData.config;

import hzau.sa.msg.config.Swagger2Config;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SensorDataSwagger extends Swagger2Config {
    @Bean
    public Docket sensorDataDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("HZAU-Smart-Agriculture 物联网接口")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("hzau.sa.sensorData"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build().securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }
}
