package hzau.sa.sensorData.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.sensorData.entity.VideoMonitorModel;

/**
 * videoMonitor 服务实现类
 * @author lvhao
 * @date 2020-09-04
 */
public interface VideoMonitorServiceSensorData {
    public IPage<VideoMonitorModel> pageByStudentId(Page<VideoMonitorModel> page,String studentId);
    public IPage<VideoMonitorModel> pageByTeacherId(Page<VideoMonitorModel> page,String teacherId);
}