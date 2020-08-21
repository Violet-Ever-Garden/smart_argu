package hzau.sa.trainingReport;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@EnableTransactionManagement
//@ComponentScan(basePackages = {"hzau.sa.*"})
//@MapperScan(basePackages = {"hzau.sa.*.dao"})
//@EnableSwagger2
//@SpringBootApplication
public class TrainingReportApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrainingReportApplication.class,args);
    }
}
