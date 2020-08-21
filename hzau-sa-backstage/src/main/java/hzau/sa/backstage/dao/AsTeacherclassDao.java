package hzau.sa.backstage.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.ClassGradeModel;
import hzau.sa.backstage.entity.TeacherClassModel;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.AsTeacherclassVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * (修改为自己的说明) Mapper 接口
 * @author haokai
 * @date 2020-08-13
 */
@Mapper
public interface AsTeacherclassDao extends BaseMapper<AsTeacherclassVO> {

    IPage<TeacherClassModel> listByTeacherId(
            Page<TeacherClassModel> page, @Param("teacherId") String teacherId,
            @Param("keyword")String keyword,@Param("gradeName") String gradeName);


    IPage<ClassGradeModel> listClassWithoutTeacher(Page<ClassGradeModel> page,@Param("keyword")String keyword,@Param("gradeName")String gradName,@Param("teacherId")String teacherId);
}