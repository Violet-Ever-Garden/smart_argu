package hzau.sa.backstage.dao;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.ClassVO;
import org.springframework.stereotype.Repository;

/**
 * class Mapper 接口
 * @author lvhao
 * @date 2020-08-12
 */
@Repository
@Mapper
public interface ClassDao extends BaseMapper<ClassVO> {

}