package hzau.sa.backstage.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.RegionModel;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.RegionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * (修改为自己的说明) Mapper 接口
 * @author wyh
 * @date 2020-08-28
 */
@Repository
@Mapper
public interface RegionDao extends BaseMapper<RegionVO> {
    public IPage<RegionModel> page(Page<RegionModel> page,@Param("regionName") String regionName);
    public Integer querySchoolIdByBaseName(@Param("baseName") String baseName);
    public Integer queryBaseIdByBaseName(@Param("baseName") String baseName);
}