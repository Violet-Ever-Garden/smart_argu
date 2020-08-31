package hzau.sa.backstage.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import hzau.sa.backstage.entity.SensorVO;
import hzau.sa.backstage.entity.SensorWrapper;
import hzau.sa.backstage.dao.SensorDao;
import hzau.sa.backstage.entity.SensorModel;
import hzau.sa.backstage.service.impl.SensorServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author haokai
 */
public class SensorListener extends AnalysisEventListener<SensorWrapper> {
    private SensorDao sensorDao;
    private SensorServiceImpl sensorService;
    private List<SensorVO> list= new ArrayList<>();
    private static final int BATCH_COUNT = 5;

    public SensorListener(SensorDao sensorDao,SensorServiceImpl sensorService){
        this.sensorDao = sensorDao;
        this.sensorService = sensorService;
    }


    @Override
    public void invoke(SensorWrapper sensorWrapper, AnalysisContext analysisContext) {
        System.out.println(sensorWrapper.toString());
        SensorVO sensorVO = new SensorVO(sensorWrapper);
        sensorVO.setDeviceTypeId(sensorDao.getDeviceTypeIdByName(sensorWrapper.getDeviceTypeName()));
        sensorVO.setBaseId(sensorDao.getBaseIdByName(sensorWrapper.getBaseName()));
        sensorVO.setRegionId(sensorDao.getRegionIdByName(sensorWrapper.getRegionName()));
        list.add(sensorVO);

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
        sensorService.saveBatch(list);
    }


}
