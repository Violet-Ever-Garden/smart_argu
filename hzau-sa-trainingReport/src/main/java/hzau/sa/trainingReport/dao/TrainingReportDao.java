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

    /**
     * 根据作物Id查询作物名
     * @param cropId
     * @return
     */
    public String queryCropNameById(Integer cropId);

    /**
     * 根据班级id查询班级名称
     * @param classId
     * @return
     */
    public String queryClassNameById(Integer classId);

    /**
     * 根据班级id 查询年级名称
     * @param classId
     * @return
     */
    public String queryGradeNameByClassId(Integer classId);

}