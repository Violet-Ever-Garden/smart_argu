package hzau.sa.backstage.dao;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.SchoolVO;
import org.springframework.stereotype.Repository;

/**
 * (修改为自己的说明) Mapper 接口
 * @author wyh
 * @date 2020-08-25
 */
@Repository
@Mapper
public interface SchoolDao extends BaseMapper<SchoolVO> {

}