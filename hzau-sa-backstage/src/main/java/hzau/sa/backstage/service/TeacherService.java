package hzau.sa.backstage.service;

import com.baomidou.mybatisplus.extension.api.R;
import hzau.sa.backstage.entity.TeacherWrapper;
import hzau.sa.msg.entity.Result;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.backstage.dao.TeacherDao;
import hzau.sa.backstage.entity.TeacherVO;

/**
 * teacher 服务实现类
 * @author wuyihu
 * @date 2020-08-13
 */
public interface TeacherService  {
    /**
     * 分页显示老师
     */
    public Result page(int pageNo);
    /**
     * 按名字分页老师
     */
    public Result pageByName(String name,int pageNo);
    /**
     * 增加老师
     */
    public Result addTeacher(TeacherWrapper teacherWrapper);
    /**
     * 删除老师
     */
    public Result deleteTeacher(String teacherId);
    /**
     * 批量删除老师
     */
    public Result deleteTeachers(String[] teacherIds);
    /**
     * 更新老师
     */
    public Result updateTeacher(TeacherWrapper teacherWrapper);

}