package hzau.sa.trainingReport.dao;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.trainingReport.entity.MeasuremanageVO;

import java.util.List;

/**
 * Measuremanage Mapper 接口
 * @author lvhao
 * @date 2020-08-18
 */
@Mapper
public interface MeasuremanageDao extends BaseMapper<MeasuremanageVO> {

    /**
     * 通过措施名字查询措施ID
     * @param measureName
     * @return
     */
    public Integer queryMeasureIdByMeasureName(String measureName);

    /**
     * 根据措施Id查询措施名称
     * @param measureId
     * @return
     */
    public String queryMeasureNameById(Integer measureId);

    /**
     * 根据作物名字查询作物ID
     * @param cropName
     * @return
     */
    public Integer queryCropIdByCropName(String cropName);

    /**
     * 根据作物id查询作物名称
     * @param id
     * @return
     */
    public String queryCropNameByCropId(Integer id);

    /**
     * 根据年级名称查询ID
     * @param gradeName
     * @return
     */
    public Integer queryGradeIdByName(String gradeName);

    /**
     * 根据年级ID查询名称
     * @param gradeId
     * @return
     */
    public String queryGradeNameById(Integer gradeId);

    /**
     * 根据班级名称查询ID
     * @param className
     * @return
     */
    public Integer queryClassIdByName(String className);

    /**
     * 根据班级ID查询名称
     * @param classId
     * @return
     */
    public String queryClassNameById(Integer classId);

    /**
     * 根据班级ID查询班级所属地块
     * @param classId
     * @return
     */
    public List<String> queryClassFieldByClassId(Integer classId);

}