package hzau.sa.sensorData.dao;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.sensorData.entity.ControlInteractionModel;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.sensorData.entity.ControlInteractionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * (修改为自己的说明) Mapper 接口
 * @author lvhao
 * @date 2020-09-04
 */
@Repository
@Mapper
public interface ControlInteractionDao extends BaseMapper<ControlInteractionVO> {
    public IPage<ControlInteractionModel> pageByClassId(Page<ControlInteractionModel> page,@Param("classId") Integer classId);
    public IPage<ControlInteractionModel> pageByTeacherId(Page<ControlInteractionModel> page,@Param("teacherId") String teacherId);
    public Integer getClassIdByStudentId(@Param("studentId") String studentId);
}