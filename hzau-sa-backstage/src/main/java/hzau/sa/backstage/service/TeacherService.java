package hzau.sa.backstage.service;

import hzau.sa.backstage.entity.TeacherWrapper;
import hzau.sa.msg.entity.Result;
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
    public Result updateTeacherBackstage(TeacherWrapper teacherWrapper);
    public Result updateTeacherAccount(String teacherId,String teacherName,String oldPassword,String newPassword,MultipartFile file);
}