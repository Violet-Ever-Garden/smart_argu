package hzau.sa.backstage.dao;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.AsClasscropVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (修改为自己的说明) Mapper 接口
 * @author haokai
 * @date 2020-08-29
 */
@Mapper
public interface AsClasscropDao extends BaseMapper<AsClasscropVO> {

    Integer queryCropIdByName(@Param("cropName") String cropName);

    List<Integer> queryCropIdsByClassId(@Param("classId") Integer classId);

    List<Integer> queryFieldIdsByNames(@Param("list") List<String> classCrop);

    List<String> queryNameById(@Param("classId") Integer classId);
}