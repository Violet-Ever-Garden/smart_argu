package hzau.sa.stater;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan("hzau.sa.*")
@MapperScan("hzau.sa.*")
@SpringBootApplication
@EnableSwagger2
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
