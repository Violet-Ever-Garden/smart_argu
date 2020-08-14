package hzau.sa.backstage.service;

import hzau.sa.backstage.entity.StudentWrapper;
import hzau.sa.msg.entity.Result;
import hzau.sa.backstage.entity.StudentVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * student 服务实现接口
 * @author wuyihu
 * @date 2020-08-13
 */
public interface StudentService  {
    public Result updateStudent(StudentWrapper studentWrapper);
    public Result addStudent(StudentWrapper studentWrapper);
    public Result page(int pageNo);
    public Result pageByGrade(String gradeName,int pageNo);
    public Result pageByClasses(String className,int pageNo);
    public Result pageByName(String name,int pageNo);
    public Result addStudentByTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
    public Result deleteStudent(String studentId);
    public Result deleteStudents(String[] studentIds);
    public Result downloadTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}