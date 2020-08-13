package hzau.sa.backstage.dao;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.AsClassfieldVO;

import java.util.List;

/**
 * 班级关联地块 Mapper 接口
 * @author lvhao
 * @date 2020-08-12
 */
@Mapper
public interface AsClassfieldDao extends BaseMapper<AsClassfieldVO> {

    /**
     * 按班级id查询地块 id
     * @param classId
     * @return
     */
    List<String> queryFieldIdsByClassId(Integer classId);

    /**
     * 按照地块名查找地块ID
     * @param fieldName
     * @return
     */
    Integer queryFieldIdByName(String fieldName);

}