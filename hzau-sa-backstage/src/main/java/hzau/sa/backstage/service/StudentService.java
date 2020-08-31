package hzau.sa.backstage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.StudentModel;
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
    public Result addStudentByTemplate(MultipartFile multipartFile, StudentServiceImpl studentService);
    public Result deleteStudent(String studentId);
    public Result deleteStudents(String[] studentIds);
    public Result updateStudentBackstage(StudentWrapper studentWrapper);
    public Result updateStudentAccount(String studentId,String studentName,String oldPassword,String newPassword,MultipartFile file);
    public Result downloadTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
    public IPage<StudentModel> page(Page<StudentModel> page, String studentName, String gradeName, String className);
}