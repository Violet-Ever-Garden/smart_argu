package hzau.sa.sensorData.thread;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import hzau.sa.sensorData.dao.GatewayDao;
import hzau.sa.sensorData.entity.GatewayVO;
import hzau.sa.sensorData.entity.SensorDataRecord;
import hzau.sa.sensorData.service.impl.SensorDataRecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class SensorDataThread {

    @Autowired
    SensorDataRecordServiceImpl sensorDataRecordService;
    @Autowired
    GatewayDao  gatewayDao;


    @Scheduled(cron="0 */30 * * * ?")
    public void getSensorDataByXML(){
        System.out.println(System.currentTimeMillis());
        QueryWrapper<GatewayVO> queryWrapper = new QueryWrapper<>();
        List<String> logos = gatewayDao.queryAll();
        for( String logo : logos){
            System.out.println(logos);
            List<SensorDataRecord> nowSensorData = sensorDataRecordService.getLatestDataFromIOT(logo);
            sensorDataRecordService.saveBatch(nowSensorData);
        }
    }
}
