package hzau.sa.trainingReport.dao;

import hzau.sa.trainingReport.entity.ExportReport;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.trainingReport.entity.TrainingReportVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TrainingReport Mapper 接口
 * @author lvhao
 * @date 2020-08-19
 */
@Repository
@Mapper
public interface TrainingReportDao extends BaseMapper<TrainingReportVO> {

    /**
     * 根据作物Id 班级Id 老师Id 查找实训报告
     * @param cropId 作物Id
     * @param classId 班级ID
     * @param teacherId 老师Id
     * @return
     */
    List<ExportReport> queryExportReportByCropClassTeacherIds(@Param("cropId") Integer cropId,
                                                         @Param("classId") Integer classId,
                                                         @Param("teacherId") String teacherId);

}