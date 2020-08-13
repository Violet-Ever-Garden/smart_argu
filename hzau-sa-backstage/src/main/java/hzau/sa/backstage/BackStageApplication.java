package hzau.sa.backstage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
<<<<<<< HEAD
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ComponentScan(basePackages = {"hzau.sa.*"})
@MapperScan(basePackages = {"hzau.sa.*.dao"})
=======
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
>>>>>>> origin/haokai-dev
@SpringBootApplication

public class BackStageApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackStageApplication.class,args);
    }
}
