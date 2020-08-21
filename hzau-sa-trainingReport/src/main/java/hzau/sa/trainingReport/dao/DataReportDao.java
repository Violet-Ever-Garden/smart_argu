package hzau.sa.trainingReport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.trainingReport.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * (修改为自己的说明) Mapper 接口
 * @author lvhao
 * @date 2020-08-14
 */
@Mapper
public interface DataReportDao extends BaseMapper<DataReportVO> {

    IPage<DataReportModel> selectDataReportModelPage(Page<DataReportModel> page, @Param("cropId") int cropId, @Param("studentId") String studentId);

    List<CropDataReport> selectCropDataReportList(ArrayList<Integer> ids);

    List<CropIdName> selectCropIdNameList(ArrayList<Integer> ids);

    IPage<StudentReportModel> selectStudentByClassAndCrop(Page<StudentReportModel> page,@Param("classId") int classId, @Param("cropId") int cropId, @Param("studentName") String studentName);

    List<ClassDataReport> selectStudentByClass(@Param("cropId") int cropId,@Param("list")ArrayList<Integer> list,@Param("teacherId") String teacherId);

    List<String> selectParametersByCropId(@Param("cropId")int cropId);

}
