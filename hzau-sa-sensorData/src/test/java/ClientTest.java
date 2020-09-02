import hzau.sa.sensorData.entity.SensorDataRecord;
import hzau.sa.sensorData.service.SensorService;
import hzau.sa.sensorData.util.ClientAxis2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class ClientTest {
    @Autowired
    SensorService sensorService;
    @Test
    void cTest(){
        String userName="liujianxiao";
        //密码
        String pasword = "liujianxiao123";
        //url地址
        String url="http://iot.klha.net:8080/services/iotDataService";
        //服务器端发布的方法
        String method="CurAllData";
        String xml = ClientAxis2.sendService(userName, pasword, url, method);
        List<SensorDataRecord> sensorDataRecords = sensorService.parsingXMLByCurAll(xml);
        sensorDataRecords.forEach(System.out::println);
    }
}
