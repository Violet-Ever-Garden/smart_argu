package hzau.sa.backstage.dao;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.AsClassfieldVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (修改为自己的说明) Mapper 接口
 * @author haokai
 * @date 2020-08-29
 */
@Mapper
public interface AsClassfieldDao extends BaseMapper<AsClassfieldVO> {
    int queryFieldIdByName(@Param("fieldName")String fieldName);

    List<String> queryFieldNamesByClassId(@Param("classId") Integer classId);

    List<Integer> queryFieldIdsByClassId(@Param("classId")Integer classId);


    List<Integer> queryFieldIdsByNames(@Param("list") List<String> fieldNames);
}