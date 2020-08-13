package hzau.sa.backstage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

<<<<<<< HEAD
@EnableTransactionManagement
@ComponentScan(basePackages = {"hzau.sa.*"})
@MapperScan(basePackages = {"hzau.sa.*.dao"})
=======
@MapperScan("hzau.sa.backstage.dao")
>>>>>>> origin/wuyihu-dev
@SpringBootApplication

public class BackStageApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackStageApplication.class,args);
    }
}
