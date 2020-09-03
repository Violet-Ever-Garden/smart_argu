package hzau.sa.trainingReport.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.trainingReport.entity.*;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
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
    public String queryGradeNameByClassId(@Param("classId")Integer classId);

    /**
     * 根据学生id查询班级id
     * @param studentId
     * @return
     */
    public Integer queryClassIdByStudentId(@Param("studentId") String studentId);

    /**
     * 根据班级id查询老师id
     * @param classId
     * @return
     */
    public String querTeacherIdByClassId(@Param("classId") Integer classId);

    /**
     * 根据学生id和批次查询实训报告id
     * @param studentId
     * @param batch
     * @return
     */
    public Integer queryTrainingReportIdBySomething(Integer cropId,String studentId,Integer batch);

    /**
     * 分页
     * @param page
     * @param corpId
     * @param studentId
     * @param startTime
     * @param endTime
     * @param reviewStatus
     * @param trainingReportName
     * @return
     */
    public IPage<TrainingReportPageWithoutFile> page(Page<TrainingReportPageWithoutFile> page,
                                                     @Param("cropId") Integer corpId,
                                                     @Param("studentId") String studentId,
                                                     @Param("startTime") String startTime,
                                                     @Param("endTime") String endTime,
                                                     @Param("reviewStatus") String reviewStatus,
                                                     @Param("trainingReportName") String trainingReportName);

    /**
     * 学生视角下的实训报告
     * @param trainingReportId
     * @param fileType
     * @return
     */
    public TrainingReportStudentView studentView(@Param("trainingReportId") Integer trainingReportId,@Param("fileType") String fileType);

    /**
     * 老师视角下的实训报告
     * @param trainingReportId
     * @param fileType
     * @return
     */
    public TrainingReportTeacherView teacherView(@Param("trainingReportId") Integer trainingReportId,@Param("fileType") String fileType);

    /**
     * 根据学生id得到老师id
     * @param studentId
     * @return
     */
    public String queryTeacherIdByStudentId(@Param("studentId") String studentId);


}