package hzau.sa.sensorData.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.sensorData.dao.VideoMonitorDao;
import hzau.sa.sensorData.entity.VideoMonitorModel;
import hzau.sa.sensorData.entity.VideoMonitorVO;
import hzau.sa.sensorData.service.VideoMonitorService;
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
public class VideoMonitorServiceImpl extends ServiceImpl<VideoMonitorDao, VideoMonitorVO> implements VideoMonitorService {
    @Autowired
    private VideoMonitorDao videoMonitorDao;
    @Override
    public IPage<VideoMonitorModel> pageByStudentId(Page<VideoMonitorModel> page, String studentId){
        return videoMonitorDao.pageByStudntId(page,studentId);
    }
    @Override
    public IPage<VideoMonitorModel> pageByTeacherId(Page<VideoMonitorModel> page, String teacherId){
        return videoMonitorDao.pageByTeacherId(page,teacherId);
    }
}
