package hzau.sa.backstage.dao;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.GradeVO;
import org.springframework.stereotype.Repository;

/**
 * GradeMapper 接口
 * @author lvhao
 * @date 2020-08-12
 */
@Repository
@Mapper
public interface GradeDao extends BaseMapper<GradeVO> {

    /**
     * 按照主键查询 年级名称
     * @param id
     * @return
     */
    String selectGradeNameById(Integer id);

    /**
     * 按照年级名称查询主键
     * @param gradeName
     * @return
     */
    Integer selectGradeIdByName(String gradeName);

}