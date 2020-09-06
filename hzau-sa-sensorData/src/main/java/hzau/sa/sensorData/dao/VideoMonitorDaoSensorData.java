package hzau.sa.sensorData.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.sensorData.entity.VideoMonitorModel;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.sensorData.entity.VideoMonitorVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * (修改为自己的说明) Mapper 接口
 * @author lvhao
 * @date 2020-09-04
 */
@Repository
@Mapper
public interface VideoMonitorDaoSensorData extends BaseMapper<VideoMonitorVO> {
    public IPage<VideoMonitorModel> pageByStudntId(Page<VideoMonitorModel> page,@Param("studentId") String studentId);
    public IPage<VideoMonitorModel> pageByTeacherId(Page<VideoMonitorModel> page,@Param("teacherId") String teacherId);
}