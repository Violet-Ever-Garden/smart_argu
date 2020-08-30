package hzau.sa.backstage.dao;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.AsClasscontrolinteractionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (修改为自己的说明) Mapper 接口
 * @author haokai
 * @date 2020-08-29
 */
@Mapper
public interface AsClasscontrolinteractionDao extends BaseMapper<AsClasscontrolinteractionVO> {

    Integer queryControlInteractionIdByName(@Param("remoteControlDeviceName")String waterFertilizerMachine);

    List<String> queryNamesByClassId(@Param("classId") Integer classId);

    List<Integer> queryControlinteractionIdsByClassId(@Param("classId") Integer classId);

    List<Integer> queryControlinteractionIdsByNames(@Param("list") List<String> classWaterFertilizerMachine);
}