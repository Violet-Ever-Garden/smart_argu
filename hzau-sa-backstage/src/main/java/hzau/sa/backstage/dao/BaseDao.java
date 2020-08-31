package hzau.sa.backstage.dao;

import hzau.sa.backstage.entity.Base;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * (修改为自己的说明) Mapper 接口
 * @author lvhao
 * @date 2020-08-26
 */
@Repository
@Mapper
public interface BaseDao extends BaseMapper<Base> {
}