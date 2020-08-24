package hzau.sa.backstage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.StudentModel;
import hzau.sa.backstage.entity.StudentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * (修改为自己的说明) Mapper 接口
 * @author wuyihu
 * @date 2020-08-13
 */
@Repository
@Mapper
public interface StudentDao extends BaseMapper<StudentVO> {
     IPage<StudentModel> page(Page<StudentModel> page, @Param("studentName") String studentName,
                              @Param("gradeName") String gradeName, @Param("className") String className);
}