package hzau.sa.backstage.service;

import com.baomidou.mybatisplus.extension.api.R;
import hzau.sa.backstage.entity.TeacherWrapper;
import hzau.sa.msg.entity.Result;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.backstage.dao.TeacherDao;
import hzau.sa.backstage.entity.TeacherVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * teacher 服务实现类
 * @author wuyihu
 * @date 2020-08-13
 */
public interface TeacherService  {
    public Result addTeacher(TeacherWrapper teacherWrapper);
    public Result deleteTeacher(String teacherId);
    public Result deleteTeachers(String[] teacherIds);
    public Result updateTeacher(TeacherWrapper teacherWrapper, MultipartFile file);
}