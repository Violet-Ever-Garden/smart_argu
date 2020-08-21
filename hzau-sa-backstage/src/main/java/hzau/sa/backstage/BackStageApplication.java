package hzau.sa.backstage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableTransactionManagement
//@ComponentScan(basePackages = {"hzau.sa.*"})
//@MapperScan(basePackages = {"hzau.sa.*.dao"})
//@SpringBootApplication
public class BackStageApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackStageApplication.class,args);
    }
}
