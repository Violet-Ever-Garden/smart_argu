package hzau.sa.sensorData.service.impl;

import cn.hutool.core.convert.Convert;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.msg.common.RoleConstant;
import hzau.sa.sensorData.common.KLHAConstant;
import hzau.sa.sensorData.common.SensorType;
import hzau.sa.sensorData.dao.GatewayDao;
import hzau.sa.sensorData.dao.SensorDataRecordDao;
import hzau.sa.sensorData.entity.GatewayNameAddress;
import hzau.sa.sensorData.entity.GatewayVO;
import hzau.sa.sensorData.entity.SensorDataPage;
import hzau.sa.sensorData.entity.SensorDataRecord;
import hzau.sa.sensorData.service.SensorDataRecordService;
import hzau.sa.sensorData.util.ClientAxis2;
import hzau.sa.sensorData.util.XmlPrasing;
import org.apache.woden.xml.ArgumentArrayAttr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SensorDataRecordServiceImpl extends ServiceImpl<SensorDataRecordDao, SensorDataRecord> implements SensorDataRecordService {


    @Autowired
    private SensorDataRecordDao sensorDataRecordDao;
    @Resource
    private GatewayDao gatewayDao;
    /**
     * 获取单一传感器的历史数据
     * @return
     *
     */
    public List<SensorDataRecord> getOneSensorHistoryData(String logo,String startTime ,String endTime ,String sensorName){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(startTime,formatter);
        LocalDateTime end = LocalDateTime.parse(endTime,formatter);
        QueryWrapper<SensorDataRecord> sensorDataRecordQueryWrapper = new QueryWrapper<>();
        sensorDataRecordQueryWrapper.lambda()
                .eq(SensorDataRecord::getGatewayLogo,logo)
                .eq(SensorDataRecord::getSensorName,sensorName)
                .between(SensorDataRecord::getDataTime,start,end);
        return sensorDataRecordDao.selectList(sensorDataRecordQueryWrapper);
    }

    /**
     *获取某一网关历史数据
     * @return
     */
    public HashMap<String, List<SensorDataRecord>> getOneGatewayHistoryData(String logo, String startTime , String endTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(startTime,formatter);
        LocalDateTime end = LocalDateTime.parse(endTime,formatter);
        return transform(sensorDataRecordDao.getOneGatewayHistoryData(logo,start,end));
    }
    /**
     *获取某一网关历史数据+分页
     * @return
     */
    public SensorDataPage getOneGatewayHistoryDataPage(String logo, String startTime , String endTime,int limit,int page){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(startTime,formatter);
        LocalDateTime end = LocalDateTime.parse(endTime,formatter);
        return page(transform(sensorDataRecordDao.getOneGatewayHistoryData(logo,start,end)),limit,page);
    }

    /**
     *按小时获取某网关历史数据+分页
     */
    public SensorDataPage getGatewayDataByHoursPage(String gatewayAddress, Long hours, int page, int limit) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime before = now.minus(hours, ChronoUnit.HOURS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" );
        return getOneGatewayHistoryDataPage(gatewayAddress,before.format(formatter),now.format(formatter),limit,page);
    }

    /**
     * 按小时获取某一传感器的数据
     */
    public List<SensorDataRecord> getOneSensorDataByHours(String logo,String sensorName,long hours){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime before = now.minus(hours, ChronoUnit.HOURS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" );
        return getOneSensorHistoryData(logo,before.format(formatter), now.format(formatter),sensorName );
    }

    /**
     *按小时获取某一网关的数据
     */
    public HashMap<String, List<SensorDataRecord>> getGatewayDataByHours(String gatewayAddress, Long hours) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime before = now.minus(hours, ChronoUnit.HOURS);
        return  transform(sensorDataRecordDao.getOneGatewayHistoryData(gatewayAddress,before,now));
    }

    /**
     *获取某网关最新数据
     */
    public List<SensorDataRecord> getNowSensorData(String logo){
        return sensorDataRecordDao.getNowSensorData(logo);
    }

    /**
     *从iot平台获取最新的数据
     */
    public List<SensorDataRecord> getLatestDataFromIOT(String logo){
        ArrayList<String> params = new ArrayList<>();
        params.add(logo);
        String xml = ClientAxis2.sendService(params,KLHAConstant.GATEWAY_DATA_METHOD);
        return XmlPrasing.parsingXMLByCurAll(xml);
    }

    public HashMap<String, List<SensorDataRecord>> transform(List<SensorDataRecord> list){
        HashMap<String, List<SensorDataRecord>> sensorDataRecords = new HashMap<>();
        for(Map.Entry<String,String> entry:SensorType.sensorType.entrySet()){
            sensorDataRecords.put(entry.getValue(),new ArrayList<SensorDataRecord>());
        }
        for(SensorDataRecord sensorDataRecord: list){
            sensorDataRecords.get(sensorDataRecord.getSensorName())
                    .add(sensorDataRecord);
        }
        return sensorDataRecords;
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

    public List<String> getRegionById(String role, String id) {
        if(RoleConstant.TEACHER.equals(role)||RoleConstant.ADMIN.equals(role)){
            return sensorDataRecordDao.selectRegionByTeacherId(id);
        }else if(RoleConstant.STUDENT.equals(role)){
            return sensorDataRecordDao.selectRegionByStudentId(id);
        }else{
            return null;
        }
    }

    public List<GatewayNameAddress> getGatewayByRegion(String regionName){
        return sensorDataRecordDao.selectGatewayByRegionName(regionName);
    }
    public void exportGatewayDataByHours(String gatewayAddress, Long hours, HttpServletResponse httpServletResponse) {
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


}
