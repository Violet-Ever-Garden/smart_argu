package hzau.sa.backstage.service;

import hzau.sa.msg.entity.Result;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.backstage.dao.StudentDao;
import hzau.sa.backstage.entity.StudentVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * student 服务实现接口
 * @author wuyihu
 * @date 2020-08-13
 */
public interface StudentService  {
    /**
     * 分页列表
     */
    public Result page(int pageNo);

    /**
     * 返回所有年级
     */
    public Result getAllGrades();

    /**
     * 按年级分页
     */
    public Result pageByGrade(int gradeId,int pageNo);

    /**
     * 返回所有班级
     */
    public Result gerAllClasses();

    /**
     * 按班级分页
     */

    public Result pageByClasses(int classId,int pageNo);

    /**
     * 按名字查找分页
     */

    public Result pageByName(String name,int pageNo);

    /**
     * 添加学生
     */
    public Result addStudent(StudentVO studentVO);


    /**
     * 从模板中添加学生
     */
    public Result addStudentByTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    /**
     * 删除学生
     */
    public Result deleteStudent(String studentId);

    /**
     * 批量删除学生
     */
    public Result deleteStudents(String[] studentIds);

    /**
     * 更新学生
     */
    public Result updateStudent(StudentVO studentVO);

    /**
     * 模板下载
     */
    public Result downloadTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

}