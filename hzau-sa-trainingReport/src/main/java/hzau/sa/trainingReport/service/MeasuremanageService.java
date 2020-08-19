package hzau.sa.trainingReport.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import hzau.sa.trainingReport.entity.ClassOfTeacher;
import hzau.sa.trainingReport.entity.MeasureManageRequest;
import hzau.sa.trainingReport.entity.MeasureManageResponse;
import hzau.sa.trainingReport.entity.MeasuremanageVO;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * measuremanage 服务实现类
 * @author lvhao
 * @date 2020-08-18
 */
public interface MeasuremanageService  extends IService<MeasuremanageVO> {

    /**
     * 增加一条措施
     * @param measureManageRequest
     * @return
     * @throws IOException
     */
    public boolean insertMeasure(MeasureManageRequest measureManageRequest) throws IOException;

    /**
     * 查询学生关于一种作物的所有措施
     * @param studentId
     * @param cropName
     * @return
     */
    public List<MeasureManageResponse> queryMeasure(String studentId,String cropName);

    /**
     * 更新一条措施信息
     * @param measureManageRequest
     * @param measureManageId
     * @param ids
     * @return
     * @throws IOException
     */
    public boolean updateMeasure(MeasureManageRequest measureManageRequest,Integer measureManageId,String[] ids) throws IOException;

    /**
     * 删除学生措施
     * @param measureManageId 要删除措施的ID
     * @return
     */
    public boolean deleteMeasure(Integer measureManageId);

    /**
     * 根据年级名称查询ID
     * @param gradeName
     * @return
     */
    public Integer queryGradeIdByName(String gradeName);

    /**
     * 根据班级名称查询ID
     * @param className
     * @return
     */
    public List<Integer> queryClassIdByName(String className);

    /**
     * 根据学生姓名查询学生Id
     * @param studentName
     * @return
     */
    public List<String> queryStudentIdByName(String studentName);

    /**
     * 查询老师所管理的班级
     * @param page
     * @param queryWrapper
     * @return
     */
    public Map queryClassByTeacherId(Page page, QueryWrapper queryWrapper);

    /**
     * 根据班级ID 查询学生
     * @param page
     * @param queryWrapper
     * @return
     */
    public Map queryStudentByClassId(Page page,QueryWrapper queryWrapper);
}