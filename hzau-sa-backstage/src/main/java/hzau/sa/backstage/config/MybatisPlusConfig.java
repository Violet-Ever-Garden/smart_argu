package hzau.sa.backstage.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatisplus 的一些配置
 * @author wuyihu
 * @date 2020.08.12 16:11
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 配置分页
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
