package hzau.sa.trainingReport.dao;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.trainingReport.entity.StudentVO;

/**
 * Student Mapper 接口
 * @author lvhao
 * @date 2020-08-19
 */
@Mapper
public interface TrainingReportStudentDao extends BaseMapper<StudentVO> {

}