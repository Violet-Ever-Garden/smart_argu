package hzau.sa.trainingReport.dao;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.trainingReport.entity.TrainingReportVO;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Repository;

/**
 * (修改为自己的说明) Mapper 接口
 * @author wuyihu
 * @date 2020-08-19
 */
@Repository
@Mapper
public interface TrainingReportDao extends BaseMapper<TrainingReportVO> {

}