package hzau.sa.sensorData.service;

import hzau.sa.msg.common.RoleConstant;
import hzau.sa.sensorData.common.KLHAConstant;
import hzau.sa.sensorData.common.SensorType;
import hzau.sa.sensorData.dao.SensorDataDao;
import hzau.sa.sensorData.entity.GatewayNameAddress;
import hzau.sa.sensorData.entity.SensorDataRecord;
import hzau.sa.sensorData.util.ClientAxis2;
import hzau.sa.sensorData.util.XmlPrasing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 传感器业务逻辑
 * @author haokai
 */
@Service
public class SensorService {
    @Autowired
    SensorDataDao sensorDataDao;


    public List<SensorDataRecord> getNowSensorData(String logo){
        ArrayList<String> params = new ArrayList<>();
        params.add(logo);
        String xml = ClientAxis2.sendService(params,KLHAConstant.GATEWAY_DATA_METHOD);
        List<SensorDataRecord> sensorDataRecords = XmlPrasing.parsingXMLByCurAll(xml);
        return sensorDataRecords;
    }

    /**
     * 获取单一传感器的历史数据
     * @return
     *
     */
    public List<SensorDataRecord> getOneSensorHistoryData(String logo,String startTime ,String endTime ,String sensorName){
        ArrayList<String> params = new ArrayList<>();
        params.add(logo);
        params.add(startTime);
        params.add(endTime);
        String xml = ClientAxis2.sendService(params,KLHAConstant.GATEWAY_HISTORY_METHOD);
        String sensorChannel  = "";
        for (Map.Entry<String,String> entry : SensorType.sensorType.entrySet()){
            if(sensorName.equals(entry.getValue())){
                sensorChannel = entry.getKey();
                break;
            }
        }
        List<SensorDataRecord> sensorDataRecords = XmlPrasing.parsingXMLBySensor(xml,sensorChannel);
        return sensorDataRecords;
    }

    /**
     *获取某一网关历史数据
     * @return
     */
    public HashMap<String, List<SensorDataRecord>> getOneGatewayHistoryData(String logo,String startTime ,String endTime){
        ArrayList<String> params = new ArrayList<>();
        params.add(logo);
        params.add(startTime);
        params.add(endTime);
        String xml = ClientAxis2.sendService(params,KLHAConstant.GATEWAY_HISTORY_METHOD);
        HashMap<String, List<SensorDataRecord>> stringListHashMap = XmlPrasing.parsingXMLByHistory(xml);
        return stringListHashMap;
    }

    public List<SensorDataRecord> asdasdasd(String logo,String starttime ,String endtime){
        ArrayList<String> params = new ArrayList<>();
        params.add(logo);
        params.add(starttime);
        params.add(endtime);
        String xml = ClientAxis2.sendService(params,KLHAConstant.GATEWAY_HISTORY_METHOD);
        List<SensorDataRecord> sensorDataRecords = XmlPrasing.parsingXMLByCurAll(xml);
        return sensorDataRecords;
    }

    public List<SensorDataRecord> getCurAll(){
        String xml = ClientAxis2.sendService(new ArrayList<>(), KLHAConstant.GET_ALL_METHOD);
        List<SensorDataRecord> sensorDataRecords = XmlPrasing.parsingXMLByCurAll(xml);
        return sensorDataRecords;
    }


    public List<String> getRegionById(String role, String id) {
        if(RoleConstant.TEACHER.equals(role)||RoleConstant.ADMIN.equals(role)){
            return sensorDataDao.selectRegionByTeacherId(id);
        }else if(RoleConstant.STUDENT.equals(role)){
            return sensorDataDao.selectRegionByStudentId(id);
        }else{
            return null;
        }
    }

    public List<GatewayNameAddress> getGatewayByRegion(String regionName){
        return sensorDataDao.selectGatewayByRegionName(regionName);
    }

    public HashMap<String, List<SensorDataRecord>> getGatewayDataByHours(String gatewayAddress, Long hours) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime before = now.minus(hours, ChronoUnit.HOURS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" );
        return getOneGatewayHistoryData(gatewayAddress, before.format(formatter), now.format(formatter));
    }

    public List<SensorDataRecord> getOneSensorDataByHours(String logo,String sensorName,long hours){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime before = now.minus(hours, ChronoUnit.HOURS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" );
        return getOneSensorHistoryData(logo, before.format(formatter), now.format(formatter),sensorName );
    }
}
