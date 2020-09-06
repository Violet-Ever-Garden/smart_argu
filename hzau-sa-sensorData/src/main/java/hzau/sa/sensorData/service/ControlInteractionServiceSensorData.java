package hzau.sa.sensorData.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.msg.entity.Result;
import hzau.sa.sensorData.entity.ControlInteractionModel;

/**
 * controlInteraction 服务实现类
 * @author lvhao
 * @date 2020-09-04
 */
public interface ControlInteractionServiceSensorData {
    public IPage<ControlInteractionModel> getAllControlInteractionByStudentId(Page<ControlInteractionModel> page, String studentId);
    public IPage<ControlInteractionModel> getAllControlInteractionByTeacherId(Page<ControlInteractionModel> page, String teacherId);
    public Result updateControlInteraction(Integer controlInteractionId,String remoteStatus);
}