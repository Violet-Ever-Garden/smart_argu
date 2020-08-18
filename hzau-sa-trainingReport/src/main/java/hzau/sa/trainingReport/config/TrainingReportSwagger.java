package hzau.sa.trainingReport.config;

import hzau.sa.msg.config.Swagger2Config;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
@Configuration
public class TrainingReportSwagger extends Swagger2Config {
    @Bean
    public Docket TrainingReportDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
<<<<<<< HEAD
                .groupName("HZAU-Smart-Agriculture 实训报告接口")
=======
                .groupName("HZAU-Smart-Agriculture 实训管理接口")
>>>>>>> origin/haokai-dev
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("hzau.sa.trainingReport"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build().securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }
}
