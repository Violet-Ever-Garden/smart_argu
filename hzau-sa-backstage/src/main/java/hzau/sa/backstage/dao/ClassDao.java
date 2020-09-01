package hzau.sa.backstage.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.ClassManage;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.ClassVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * class Mapper 接口
 * @author lvhao
 * @date 2020-08-12
 */
@Repository
@Mapper
public interface ClassDao extends BaseMapper<ClassVO> {
    IPage<ClassManage> selectClassManage(Page<ClassManage> page, @Param("className") String className, @Param("gradeName")String gradeName);
}