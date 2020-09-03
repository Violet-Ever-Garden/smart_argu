package hzau.sa.backstage.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.Base;
import hzau.sa.backstage.entity.BaseModel;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * (修改为自己的说明) Mapper 接口
 * @author wyh
 * @date 2020-08-26
 */
@Repository
@Mapper
public interface BaseDao extends BaseMapper<Base> {
    public IPage<BaseModel> page(Page<BaseModel> page,@Param("baseName") String baseName);
    public Integer querySchoolIdBySchoolName(@Param("schoolName") String schoolName);
}