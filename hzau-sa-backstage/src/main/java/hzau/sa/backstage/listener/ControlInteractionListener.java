package hzau.sa.backstage.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import hzau.sa.backstage.dao.SensorDao;
import hzau.sa.backstage.entity.ControlInteractionVO;
import hzau.sa.backstage.entity.ControlInteractionWrapper;
import hzau.sa.backstage.service.impl.ControlInteractionServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class ControlInteractionListener extends AnalysisEventListener<ControlInteractionWrapper> {
    private SensorDao sensorDao;
    private ControlInteractionServiceImpl controlInteractionService;
    private List<ControlInteractionVO> list;
    private static final int BATCH_COUNT = 5;

    public ControlInteractionListener(ControlInteractionServiceImpl controlInteractionService, SensorDao sensorDao) {
        this.controlInteractionService = controlInteractionService;
        this.sensorDao = sensorDao;
        this.list = new ArrayList<>();
    }

    @Override
    public void invoke(ControlInteractionWrapper controlInteractionWrapper, AnalysisContext analysisContext) {

        ControlInteractionVO controlInteractionVO = new ControlInteractionVO(controlInteractionWrapper);
        controlInteractionVO.setDeviceTypeId(sensorDao.getDeviceTypeIdByName(controlInteractionWrapper.getDeviceTypeName()));
        controlInteractionVO.setBaseId(sensorDao.getBaseIdByName(controlInteractionWrapper.getBaseName()));
        controlInteractionVO.setRegionId(sensorDao.getRegionIdByName(controlInteractionWrapper.getRegionName()));
        list.add(controlInteractionVO);

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
        controlInteractionService.saveBatch(list);
    }
}
