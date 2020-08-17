package hzau.sa.trainingReport.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.trainingReport.entity.DataReportModel;
import hzau.sa.trainingReport.entity.DataReportVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (修改为自己的说明) Mapper 接口
 * @author lvhao
 * @date 2020-08-14
 */
@Mapper
public interface DataReportDao extends BaseMapper<DataReportVO> {

    List<DataReportModel> selectDataReportModelPage(Page<DataReportModel> page, @Param("cropId") int cropId, @Param("studentId") String studentId);

}