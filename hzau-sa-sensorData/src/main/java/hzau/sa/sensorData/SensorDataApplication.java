package hzau.sa.sensorData;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"hzau.sa.*"})
@SpringBootApplication
public class SensorDataApplication {
    public static void main(String[] args) {
        SpringApplication.run(SensorDataApplication.class,args);
    }
}
