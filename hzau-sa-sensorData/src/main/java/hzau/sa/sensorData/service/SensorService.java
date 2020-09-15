package hzau.sa.sensorData.service;

import cn.hutool.core.convert.Convert;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import hzau.sa.msg.common.RoleConstant;
import hzau.sa.msg.excel.MyExcelUtil;
import hzau.sa.msg.util.FileUtil;
import hzau.sa.sensorData.common.KLHAConstant;
import hzau.sa.sensorData.common.SensorType;
import hzau.sa.sensorData.dao.GatewayDao;
import hzau.sa.sensorData.dao.SensorDataDao;
import hzau.sa.sensorData.entity.*;
import hzau.sa.sensorData.util.ClientAxis2;
import hzau.sa.sensorData.util.XmlPrasing;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.Duration;
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
@Slf4j
public class SensorService {
    @Autowired
    SensorDataDao sensorDataDao;
    @Resource
    private GatewayDao gatewayDao;
    @Value("${file.excel}")
    private String excelFilePath;

    public List<SensorDataRecord> getNowSensorData(String logo){
        ArrayList<String> params = new ArrayList<>();
        params.add(logo);
        String xml = ClientAxis2.sendService(params,KLHAConstant.GATEWAY_DATA_METHOD);
        return XmlPrasing.parsingXMLByCurAll(xml);
    }

    /**
     * 获取单一传感器的历史数据
     * @return
     *
     */
    public List<SensorDataRecord> getOneSensorHistoryData(String logo,String startTime ,String endTime ,String sensorName){

        String sensorChannel  = "";
        for (Map.Entry<String,String> entry : SensorType.sensorType.entrySet()){
            if(sensorName.equals(entry.getValue())){
                sensorChannel = entry.getKey();
                break;
            }
        }
        List<SensorDataRecord> sensorDataRecords = new ArrayList<>();
        //因为获取历史数据的额接口目前只能一次获取一天的数据 我们按天分次获取；
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(startTime,formatter);
        LocalDateTime end = LocalDateTime.parse(endTime,formatter);
        Duration duration = Duration.between(start,end);
        long l = duration.toHours();
        //小于24小时时只有一种状况
        if(l<24){
            ArrayList<String> params = new ArrayList<>();
            params.add(logo);
            params.add(startTime);
            params.add(endTime);
            String xml = ClientAxis2.sendService(params,KLHAConstant.GATEWAY_HISTORY_METHOD);
            return XmlPrasing.parsingXMLBySensor(sensorDataRecords,xml,sensorChannel);
        }
        long days = l/24;
        LocalDateTime temp = start.plusSeconds(-1);
        String xml;
        for(int i = 0;i<days;i++){
            ArrayList<String> params = new ArrayList<>();
            params.add(logo);
            params.add(temp.plusSeconds(1).format(formatter));
            // 86399 = 24 *60*60 -1
            params.add(temp.plusSeconds(86399).format(formatter));
            xml = ClientAxis2.sendService(params,KLHAConstant.GATEWAY_HISTORY_METHOD);
            XmlPrasing.parsingXMLBySensor(sensorDataRecords,xml,sensorChannel);
        }
        return sensorDataRecords;
    }

    /**
     *获取某一网关历史数据
     * @return
     */
    public HashMap<String, List<SensorDataRecord>> getOneGatewayHistoryData(String logo,String startTime ,String endTime){
        HashMap<String,List<SensorDataRecord>> sensorDataRecords = new HashMap<>();
        HashMap<String, String> sensorType = SensorType.sensorType;
        for(Map.Entry<String, String> entry : sensorType.entrySet()){
            sensorDataRecords.put(entry.getValue(), new ArrayList<>());
        }

        //因为获取历史数据的额接口目前只能一次获取一天的数据 我们按天分次获取；
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(startTime,formatter);
        LocalDateTime end = LocalDateTime.parse(endTime,formatter);
        Duration duration = Duration.between(start,end);
        long l = duration.toHours();
        //小于24小时时只有一种状况
        if(l<24){
            ArrayList<String> params = new ArrayList<>();
            params.add(logo);
            params.add(startTime);
            params.add(endTime);
            String xml = ClientAxis2.sendService(params,KLHAConstant.GATEWAY_HISTORY_METHOD);
            return XmlPrasing.parsingXMLByHistory(sensorDataRecords,xml);
        }
        long days = l/24;
        LocalDateTime temp = start.plusSeconds(-1);
        String xml;
        for(int i = 0;i<days;i++){
            ArrayList<String> params = new ArrayList<>();
            params.add(logo);
            params.add(temp.plusSeconds(1).format(formatter));
            // 86399 = 24 *60*60 -1
            params.add(temp.plusSeconds(86399).format(formatter));
            xml = ClientAxis2.sendService(params,KLHAConstant.GATEWAY_HISTORY_METHOD);
            XmlPrasing.parsingXMLByHistory(sensorDataRecords,xml);
        }
        return sensorDataRecords;
    }

    public List<SensorDataRecord> getCurAll(){
        String xml = ClientAxis2.sendService(new ArrayList<>(), KLHAConstant.GET_ALL_METHOD);
        return XmlPrasing.parsingXMLByCurAll(xml);
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
        System.out.println(before.format(formatter));
        System.out.println(now.format(formatter));
        return getOneSensorHistoryData(logo, before.format(formatter), now.format(formatter),sensorName );
    }

    public SensorDataPage page(HashMap<String, List<SensorDataRecord>> dataMap , int limit , int page){
        int skip = limit * (page-1);
        int end = limit * page;
        int total = 0;
        for(Map.Entry<String,List<SensorDataRecord>> entry : dataMap.entrySet()){
            total = entry.getValue().size();
            if(entry.getValue().size()<end){ end = entry.getValue().size();}
            entry.setValue(entry.getValue().subList(skip, end));
        }
        SensorDataPage sensorDataPage = new SensorDataPage();
        sensorDataPage.setTotal(total);
        int temp = total/limit;
        if(total - limit * temp>0){
            temp++;
        }
        sensorDataPage.setPages(Convert.toInt(temp));
        sensorDataPage.setSize(limit);
        sensorDataPage.setCurrent(page);
        sensorDataPage.setRecords(dataMap);
        return sensorDataPage;
    }







    public SensorDataPage getOneGatewayHistoryDataPage(String gatewayAddress, String startTime, String endTime, int page, int limit) {
        HashMap<String, List<SensorDataRecord>> oneGatewayHistoryData = getOneGatewayHistoryData(gatewayAddress, startTime, endTime);
        return page(oneGatewayHistoryData, limit, page);
    }

    public void exportGatewayDataByHours(String gatewayAddress, Long hours,HttpServletResponse httpServletResponse) {
        HashMap<String, List<SensorDataRecord>> gatewayDataByHours = getGatewayDataByHours(gatewayAddress, hours);
        export(gatewayDataByHours,gatewayAddress,httpServletResponse);
    }
    public void exportGatewayData(String gatewayAddress, String startTime, String endTime, HttpServletResponse httpServletResponse) {
        HashMap<String, List<SensorDataRecord>> oneGatewayHistoryData = getOneGatewayHistoryData(gatewayAddress, startTime, endTime);
        export(oneGatewayHistoryData,gatewayAddress,httpServletResponse);
    }
    public void export(HashMap<String, List<SensorDataRecord>> data,String gatewayAddress,HttpServletResponse httpServletResponse){
        ArrayList<SensorDataRecord> list = new ArrayList<>();
        for (Map.Entry<String ,List<SensorDataRecord>> entry : data.entrySet()){
            list.addAll(entry.getValue());
        }
        list.forEach(System.out::println);
        QueryWrapper<GatewayVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GatewayVO::getGatewayAddress,gatewayAddress);
        GatewayVO gatewayVO = gatewayDao.selectOne(queryWrapper);
        try{
            String fileName = URLEncoder.encode(gatewayVO.getGatewayName()+ "_"+System.currentTimeMillis(),"UTF-8")+ ".xlsx";
            httpServletResponse.setContentType("application/octet-stream");
            httpServletResponse.setCharacterEncoding("utf-8");
            httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + fileName);

            EasyExcel.write(httpServletResponse.getOutputStream(), SensorDataRecord.class).sheet().doWrite(list);
        }catch (Exception e){
            log.error(e.toString());
        }
    }


    public SensorDataPage getGatewayDataByHoursPage(String gatewayAddress, Long hours, int page, int limit) {
        HashMap<String, List<SensorDataRecord>> gatewayDataByHours = getGatewayDataByHours(gatewayAddress, hours);
        return page(gatewayDataByHours, limit, page);
    }
}
