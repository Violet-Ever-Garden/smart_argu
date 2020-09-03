package hzau.sa.backstage.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import hzau.sa.backstage.dao.VideoMonitorDao;
import hzau.sa.backstage.entity.VideoMonitorVO;
import hzau.sa.backstage.service.VideoMonitorService;
import hzau.sa.backstage.template.VideoMonitorTemplate;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LvHao
 * @Description :
 * @date 2020-08-31 11:36
 */
@Slf4j
@RequiredArgsConstructor
public class VideoMonitorListener extends AnalysisEventListener<VideoMonitorTemplate> {

    @NonNull
    private VideoMonitorDao videoMonitorDao;
    @NonNull
    private VideoMonitorService videoMonitorService;
    private List<VideoMonitorVO> videoMonitorVOList = new ArrayList<>();
    private static final int BATCH_COUNT = 5;

    @Override
    public void invoke(VideoMonitorTemplate data, AnalysisContext context) {
        VideoMonitorVO videoMonitorVO = new VideoMonitorVO();
        videoMonitorVO.setVideoMonitorDeviceName(data.getVideoMonitorDeviceName());
        videoMonitorVO.setDeviceNumber(data.getDeviceNumber());
        videoMonitorVO.setBaseId(videoMonitorDao.queryBaseIdByName(data.getBaseName()));
        videoMonitorVO.setRegionId(videoMonitorDao.queryRegionIdByName(data.getRegionName()));
        videoMonitorVO.setDeviceTypeId(videoMonitorDao.queryDeviceTypeIdByName(data.getDeviceTypeName()));
        videoMonitorVO.setHomeShow("显示");
        videoMonitorVO.setIsYingshiyun(1);
        videoMonitorVOList.add(videoMonitorVO);

        if(videoMonitorVOList.size() >= BATCH_COUNT){
            saveData();
            videoMonitorVOList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if(videoMonitorVOList.size() > 0){
            saveData();
        }
        log.info("解析完成！");
    }

    private void saveData(){
        videoMonitorService.saveBatch(videoMonitorVOList);
    }
}
