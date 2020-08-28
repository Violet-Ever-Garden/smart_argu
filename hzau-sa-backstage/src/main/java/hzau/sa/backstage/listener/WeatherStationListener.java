package hzau.sa.backstage.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import hzau.sa.backstage.dao.SensorDao;
import hzau.sa.backstage.entity.SensorWrapper;
import hzau.sa.backstage.entity.WeatherStationVO;
import hzau.sa.backstage.service.impl.WeatherStationServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class WeatherStationListener extends AnalysisEventListener<SensorWrapper> {
    private WeatherStationServiceImpl weatherStationService;
    private SensorDao sensorDao;
    private List<WeatherStationVO> list;
    private static final int BATCH_COUNT = 5;

    public WeatherStationListener(WeatherStationServiceImpl weatherStationService, SensorDao sensorDao) {
        this.weatherStationService = weatherStationService;
        this.sensorDao = sensorDao;
        this.list = new ArrayList<>();
    }

    @Override
    public void invoke(SensorWrapper sensorWrapper, AnalysisContext analysisContext) {

        WeatherStationVO weatherStationVO = new WeatherStationVO(sensorWrapper);
        weatherStationVO.setDeviceTypeId(sensorDao.getDeviceTypeIdByName(sensorWrapper.getDeviceTypeName()));
        weatherStationVO.setBaseId(sensorDao.getBaseIdByName(sensorWrapper.getBaseName()));
        weatherStationVO.setRegionId(sensorDao.getRegionIdByName(sensorWrapper.getRegionName()));
        list.add(weatherStationVO);

        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if(list.size()>0){
            saveData();
        }
        System.out.println("解析完成");
    }

    public void saveData(){
        weatherStationService.saveBatch(list);
    }
}
