package hzau.sa.sensorData.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import hzau.sa.sensorData.dao.ControlInteractionDaoSensorData;
import hzau.sa.sensorData.entity.ControlInteractionModel;
import hzau.sa.sensorData.entity.ControlInteractionVO;
import hzau.sa.sensorData.service.ControlInteractionServiceSensorData;
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
public class ControlInteractionServiceSensorDataImpl extends ServiceImpl<ControlInteractionDaoSensorData, ControlInteractionVO> implements ControlInteractionServiceSensorData {
    @Autowired
    private ControlInteractionDaoSensorData controlInteractionDaoSensorData;

    /**
     * 通过学生id得到控制交互的信息
     * @param studentId
     * @return
     */
    @Override
    public IPage<ControlInteractionModel> getAllControlInteractionByStudentId(Page<ControlInteractionModel> page, String studentId){
        Integer classId= controlInteractionDaoSensorData.getClassIdByStudentId(studentId);
        IPage<ControlInteractionModel> iPage = controlInteractionDaoSensorData.pageByClassId(page, classId);
        return iPage;
    }

    /**
     * 通过老师id得到控制交互的信息
     * @param teacherId
     * @return
     */
    @Override
    public IPage<ControlInteractionModel> getAllControlInteractionByTeacherId(Page<ControlInteractionModel> page,String teacherId){
        IPage<ControlInteractionModel> iPage = controlInteractionDaoSensorData.pageByTeacherId(page,teacherId);
        return iPage;
    }

    /**
     * 按照id更新控制交互
     * @param controlInteractionId
     * @return
     */
    @Override
    public Result updateControlInteraction(Integer controlInteractionId,String remoteStatus){
        QueryWrapper<ControlInteractionVO> controlInteractionVOQueryWrapper = new QueryWrapper<>();
        controlInteractionVOQueryWrapper.lambda().eq(ControlInteractionVO::getControlInteractionId, controlInteractionId);
        ControlInteractionVO controlInteractionVO = controlInteractionDaoSensorData.selectOne(controlInteractionVOQueryWrapper);
        controlInteractionVO.setRemoteStatus(remoteStatus);

        if (controlInteractionDaoSensorData.updateById(controlInteractionVO)==0){
            return ResultUtil.error("更新失败");
        }
        return ResultUtil.success();
    }
}
