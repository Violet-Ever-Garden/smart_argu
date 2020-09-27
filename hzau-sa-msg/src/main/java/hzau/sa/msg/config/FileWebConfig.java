package hzau.sa.msg.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author LvHao
 * @Description : 网路路径的映射配置
 * @date 2020-08-18 1:06
 */
@Configuration
public class FileWebConfig extends WebMvcConfigurationSupport {

    @Value("${file.map}")
    private String fileMap;

    @Value("${file.upload.path}")
    private String path;


    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(fileMap)
                .addResourceLocations("file:" + path);

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/csrf/**")
                .addResourceLocations("classpath:/META-INF/resources/csrf/");
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/dist/");


        super.addResourceHandlers(registry);
    }
}
