package hzau.sa.backstage.service;

import hzau.sa.backstage.entity.StudentWrapper;
import hzau.sa.backstage.service.impl.StudentServiceImpl;
import hzau.sa.msg.entity.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * student 服务实现接口
 * @author wuyihu
 * @date 2020-08-13
 */
public interface StudentService  {
    public Result addStudent(StudentWrapper studentWrapper);
    public Result addStudentByTemplate(MultipartFile multipartFile,StudentServiceImpl studentService);
    public Result deleteStudent(String studentId);
    public Result deleteStudents(String[] studentIds);
    public Result updateStudent(StudentWrapper studentWrapper,MultipartFile file);
    public Result downloadTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}