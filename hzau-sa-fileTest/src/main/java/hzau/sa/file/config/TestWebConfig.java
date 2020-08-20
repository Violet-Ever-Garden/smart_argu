package hzau.sa.file.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author LvHao
 * @Description :
 * @date 2020-08-14 16:56
 */
@Configuration
public class TestWebConfig extends WebMvcConfigurationSupport {

    @Value("${file.map}")
    private String FILE_MAP;

    @Value("${file.upload.path}")
    private String path;


    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(FILE_MAP)
                .addResourceLocations("file:" + path);

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/csrf/**")
                .addResourceLocations("classpath:/META-INF/resources/csrf/");
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/**");


        super.addResourceHandlers(registry);
    }
}
