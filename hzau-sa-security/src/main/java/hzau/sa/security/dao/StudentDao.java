package hzau.sa.security.dao;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.security.entity.StudentVO;

/**
 * (修改为自己的说明) Mapper 接口
 * @author haokai
 * @date 2020-08-10
 */
@Mapper
public interface StudentDao extends BaseMapper<StudentVO> {

}