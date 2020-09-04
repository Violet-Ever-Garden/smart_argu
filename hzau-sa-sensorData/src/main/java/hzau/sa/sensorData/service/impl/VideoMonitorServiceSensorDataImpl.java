package hzau.sa.sensorData.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.sensorData.dao.VideoMonitorDaoSensorData;
import hzau.sa.sensorData.entity.VideoMonitorModel;
import hzau.sa.sensorData.entity.VideoMonitorVO;
import hzau.sa.sensorData.service.VideoMonitorServiceSensorData;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lvhao
 * @since 2020-09-04
 */
@Service
public class VideoMonitorServiceSensorDataImpl extends ServiceImpl<VideoMonitorDaoSensorData, VideoMonitorVO> implements VideoMonitorServiceSensorData {
    @Autowired
    private VideoMonitorDaoSensorData videoMonitorDaoSensorData;
    @Override
    public IPage<VideoMonitorModel> pageByStudentId(Page<VideoMonitorModel> page, String studentId){
        return videoMonitorDaoSensorData.pageByStudntId(page,studentId);
    }
    @Override
    public IPage<VideoMonitorModel> pageByTeacherId(Page<VideoMonitorModel> page, String teacherId){
        return videoMonitorDaoSensorData.pageByTeacherId(page,teacherId);
    }
}
