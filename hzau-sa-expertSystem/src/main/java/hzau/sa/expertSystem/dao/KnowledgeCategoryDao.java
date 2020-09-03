package hzau.sa.expertSystem.dao;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.expertSystem.entity.KnowledgeCategoryVO;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * (修改为自己的说明) Mapper 接口
 * @author wuyihu
 * @date 2020-08-29
 */
@Repository
@Mapper
public interface KnowledgeCategoryDao extends BaseMapper<KnowledgeCategoryVO> {
}