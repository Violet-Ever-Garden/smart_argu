package hzau.sa.backstage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("hzau.sa.backstage.*")
@SpringBootApplication
public class BackStageApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackStageApplication.class,args);
    }
}
