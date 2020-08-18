package hzau.sa.trainingReport.dao;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.trainingReport.entity.MeasuremanageVO;

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

}