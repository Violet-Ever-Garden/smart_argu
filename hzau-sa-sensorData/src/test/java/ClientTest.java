import hzau.sa.sensorData.SensorDataApplication;
import hzau.sa.sensorData.entity.SensorDataRecord;
import hzau.sa.sensorData.service.impl.SensorService;
import hzau.sa.sensorData.service.impl.SensorDataRecordServiceImpl;
import hzau.sa.sensorData.util.ClientAxis2;
import hzau.sa.sensorData.util.XmlPrasing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest(classes = SensorDataApplication.class)
public class ClientTest {
    @Autowired
    SensorService sensorService;
    @Autowired
    SensorDataRecordServiceImpl sensorDataRecordService;
    @Test
    void cTest(){
        String userName="liujianxiao";
        //密码
        String pasword = "liujianxiao123";
        //url地址
        String url="http://iot.klha.net:8080/services/iotDataService";
        //服务器端发布的方法
        String method="CurAllData";
        String xml = ClientAxis2.sendService(new ArrayList<>(), method);
        List<SensorDataRecord> sensorDataRecords = XmlPrasing.parsingXMLByCurAll(xml);
        sensorDataRecords.forEach(System.out::println);
    }

    @Test
    void sensor(){
        SensorDataRecord sensorDataRecord = new SensorDataRecord();
        sensorDataRecord.setChannelName("11");
        sensorDataRecord.setDataTime(LocalDateTime.now());
        sensorDataRecord.setValue("1000");
        sensorDataRecord.setGatewayLogo("123123123");
        sensorDataRecordService.save(sensorDataRecord);
    }
}
