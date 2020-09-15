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

    /**
     * 通过作物id和学生id 搜索所有的调查数据
     * @param page 分页
     * @param cropId 作物id
     * @param studentId 学生id
     * @return 结果
     */
    IPage<DataReportModel> selectDataReportModelPage(Page<DataReportModel> page, @Param("cropId") int cropId, @Param("studentId") String studentId);

    /**
     * 通过作物id数组 依次按作物年级班级对存在调查数据的学生进行分类
     * @param ids 作物id数组
     * @return 统计分析列表
     */
    List<CropDataReport> selectCropDataReportList(ArrayList<Integer> ids);

    /**
     * 通过作物id数组获取作物的名称
     * @param ids 作物id数组
     * @return 作物名称
     */
    List<CropIdName> selectCropIdNameList(ArrayList<Integer> ids);

    /**
     *
     * @param page 分页
     * @param classId 班级id
     * @param cropId 作物id
     * @param studentName 学生姓名
     * @return 学生的调查数据模板
     */
    IPage<StudentReportModel> selectStudentByClassAndCrop(Page<StudentReportModel> page,@Param("classId") int classId, @Param("cropId") int cropId, @Param("studentName") String studentName);

    List<ClassDataReport> selectStudentByClass(@Param("cropId") int cropId,@Param("list")ArrayList<Integer> list,@Param("teacherId") String teacherId);

    List<String> selectParametersByCropId(@Param("cropId")int cropId);

    String selectStudentNameByStudentId(@Param("studentId")String studentId);
}
