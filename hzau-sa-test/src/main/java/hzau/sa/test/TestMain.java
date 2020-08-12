package hzau.sa.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author LvHao
 * @Description :
 * @date 2020-08-09 11:29
 */
@EnableTransactionManagement
@SpringBootApplication
@MapperScan(basePackages = {"hzau.sa.*"})
@ComponentScan(basePackages = {"hzau.sa.*"})
public class TestMain {
    public static void main(String[] args) {
        SpringApplication.run(TestMain.class,args);
    }
}
