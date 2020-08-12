package hzau.sa.msg.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.msg.entity.LogVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LvHao
 * @Description : 日志操作Mapper
 * @date 2020-08-08 18:08
 */
@Mapper
public interface LogDao extends BaseMapper<LogVO> {

}
