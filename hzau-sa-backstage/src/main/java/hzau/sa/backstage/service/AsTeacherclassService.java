package hzau.sa.backstage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.ClassGradeModel;
import hzau.sa.backstage.entity.TeacherClassModel;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.backstage.dao.AsTeacherclassDao;
import hzau.sa.backstage.entity.AsTeacherclassVO;

import java.util.List;

/**
 * as_teacherclass 服务实现类
 * @author haokai
 * @date 2020-08-13
 */
public interface AsTeacherclassService  {
    /**
     * 按老师id分页查询
     * @param page
     * @param teacherId
     * @param keyword
     * @param gradeName
     * @return
     */
    public List<TeacherClassModel> listByTeacherId(Page<TeacherClassModel> page, String teacherId, String keyword, String gradeName);

    /**
     * 分页查询无老师管理班级
     * @param keyword
     * @param gradeName
     * @return
     */
    public List<ClassGradeModel> listClassWithoutTeacher(String keyword, String gradeName);

    /**
     * 增加多个老师班级关系
     * @param asTeacherclassVOs
     * @return
     */
    public boolean saveList(List<AsTeacherclassVO> asTeacherclassVOs);


}