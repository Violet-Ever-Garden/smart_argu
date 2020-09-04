package hzau.sa.sensorData.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.sensorData.entity.VideoMonitorModel;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.sensorData.dao.VideoMonitorDao;
import hzau.sa.sensorData.entity.VideoMonitorVO;

/**
 * videoMonitor 服务实现类
 * @author lvhao
 * @date 2020-09-04
 */
public interface VideoMonitorService  {
    public IPage<VideoMonitorModel> pageByStudentId(Page<VideoMonitorModel> page,String studentId);
    public IPage<VideoMonitorModel> pageByTeacherId(Page<VideoMonitorModel> page,String teacherId);
}