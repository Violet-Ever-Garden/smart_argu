package hzau.sa.expertSystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author wyh17
 * @version 1.0
 * @date 2020/8/29 15:32
 */
//@EnableTransactionManagement
//@ComponentScan(basePackages = {"hzau.sa.*"})
//@MapperScan(basePackages = {"hzau.sa.*.dao"})
//@EnableSwagger2
//@SpringBootApplication
public class ExpertSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExpertSystemApplication.class,args);
    }
}
