package hzau.sa.backstage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.FieldModel;
import hzau.sa.backstage.entity.FieldVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * (修改为自己的说明) Mapper 接口
 * @author wuyihu
 * @date 2020-08-12
 */
@Repository
@Mapper
public interface FieldDao extends BaseMapper<FieldVO> {
    IPage<FieldModel> page(Page<FieldModel> page, @Param("fieldName") String fieldName);
}