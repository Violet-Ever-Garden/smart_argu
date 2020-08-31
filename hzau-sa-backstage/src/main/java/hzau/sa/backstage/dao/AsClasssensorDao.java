package hzau.sa.backstage.dao;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.AsClasssensorVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (修改为自己的说明) Mapper 接口
 * @author haokai
 * @date 2020-08-29
 */
@Mapper
public interface AsClasssensorDao extends BaseMapper<AsClasssensorVO> {

    int querySensorIdByName(@Param("sensorName")String sensorName);

    List<String> queryNameById(@Param("classId") Integer classId);

    List<Integer> querySensorIdsByClassId(@Param("classId") Integer classId);

    List<Integer> querySensorIdsByNames(@Param("list") List<String> sensorName);
}