package hzau.sa.backstage.dao;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.StudentVO;
import org.springframework.stereotype.Repository;

/**
 * (修改为自己的说明) Mapper 接口
 * @author wuyihu
 * @date 2020-08-13
 */
@Repository
@Mapper
public interface StudentDao extends BaseMapper<StudentVO> {

}