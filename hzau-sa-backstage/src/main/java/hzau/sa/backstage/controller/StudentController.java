package hzau.sa.backstage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hzau.sa.backstage.entity.StudentWrapper;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.entity.Result;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hzau.sa.backstage.entity.StudentVO;
import hzau.sa.backstage.service.StudentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;


/**
 *  控制器
 * @author wuyihu
 * @date 2020-08-13
 */
@Slf4j
@RestController
@RequestMapping("sys/student")
@Api(value = "-API", tags = { "学生接口" })
public class StudentController{

    @Autowired
    private StudentService studentService;

    /**
     * 分页列表
     */
    @SysLog(prefix = "分页列表")
    @ApiOperation("分页列表")
    @ApiImplicitParam(name = "pageNo",value = "即将显示的页数",paramType = "query",dataType = "int")
    @PostMapping("/page")
    public Result page(int pageNo){
        return studentService.page(pageNo);
    }



    /**
     * 按年级分页
     */
    @SysLog(prefix = "按年级分页")
    @ApiOperation("按年级分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gradeId", value = "年级的id", dataType = "int"),
            @ApiImplicitParam(name = "pageNo", value = "即将显示的页数", dataType = "int")
    }
    )
    @PostMapping("/pageByGrade")
    public Result pageByGrade(String gradeName,int pageNo){
        return studentService.pageByGrade(gradeName,pageNo);
    }


    /**
     * 按班级分页
     */

    @SysLog(prefix = "按班级分页")
    @ApiOperation("按班级分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classId", value = "班级的id", dataType = "int"),
            @ApiImplicitParam(name = "pageNo", value = "即将显示的页数", dataType = "int")
    }
    )
    @PostMapping("/pageByClass")
    public Result pageByClass(String className,int pageNo){
        return studentService.pageByClasses(className,pageNo);
    }

    /**
     * 按名字查找分页
     */
    @SysLog(prefix = "按名字分页")
    @ApiOperation("按名字分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名字", dataType = "String"),
            @ApiImplicitParam(name = "pageNo", value = "即将显示的页数", dataType = "int")
    }
    )
    @PostMapping("/pageByName")
    public Result pageByName(String name,int pageNo){
        return studentService.pageByName(name,pageNo);
    }

    /**
     * 添加学生
     */
    @SysLog(prefix = "添加学生")
    @ApiOperation("添加学生")
    @ApiImplicitParam(name = "student",value = "要添加的学生",paramType ="body",dataType = "StudentWrapper")
    @PostMapping("/addStudent")
    public Result addStudent(@RequestBody StudentWrapper student){
        return studentService.addStudent(student);
    }

    /**
     * 从模板中添加学生
     */
    @SysLog(prefix = "从模板中添加学生")
    @ApiOperation("从模板中添加学生")
    @PostMapping("/addByFile")
    public Result addByFile(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        return studentService.addStudentByTemplate(httpServletRequest,httpServletResponse);
    }


    /**
     * 删除学生
     */
    @SysLog(prefix = "删除学生")
    @ApiOperation("删除学生")
    @ApiImplicitParam(name = "studentId",value = "要删除学生的id",dataType = "String")
    @PostMapping("/deteleStudent")
    public Result deleteStudent(String studentId){
        return studentService.deleteStudent(studentId);
    }

    /**
     * 批量删除学生
     */
    @SysLog(prefix = "批量删除学生")
    @ApiOperation("批量删除学生")
    @ApiImplicitParam(name = "studentIds",value = "要删除学生的id",allowMultiple = true,dataType = "String")
    @PostMapping("/deteleStudents")
    public Result deleteStudent(String[] studentIds){
        return studentService.deleteStudents(studentIds);
    }


    /**
     * 更新学生
     */
    @SysLog(prefix = "更新学生")
    @ApiOperation("更新学生")
    @ApiImplicitParam(name = "student",value = "要更新的学生",dataType = "StudentWrapper")
    @PostMapping("/updateStudent")
    public Result updateStudent(@RequestBody StudentWrapper student){
        return studentService.updateStudent(student);
    }

    /**
     * 模板下载
     */
    @SysLog(prefix = "模板下载")
    @ApiOperation("模板下载")
    @PostMapping("/download")
    public Result downloadTemplates(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        return studentService.downloadTemplate(httpServletRequest,httpServletResponse);
    }
}