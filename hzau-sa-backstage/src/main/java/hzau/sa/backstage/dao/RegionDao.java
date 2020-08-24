package hzau.sa.backstage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.RegionVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * (修改为自己的说明) Mapper 接口
 * @author lvhao
 * @date 2020-08-21
 */
@Mapper
@Repository
public interface RegionDao extends BaseMapper<RegionVO> {

}