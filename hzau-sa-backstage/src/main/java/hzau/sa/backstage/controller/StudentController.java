package hzau.sa.backstage.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.StudentModel;
import hzau.sa.backstage.entity.StudentWrapper;
import hzau.sa.backstage.service.impl.StudentServiceImpl;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *  控制器
 * @author wuyihu
 * @date 2020-08-13
 */
@Slf4j
@RestController
@RequestMapping("/student")
@Api(value = "-API", tags = { "学生接口" })
public class StudentController extends BaseController {

    @Autowired
    private StudentServiceImpl studentService;



    /**
     * 后台分页查询
     * @param
     * @return
     */
    @SysLog(prefix = "分页查询")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentName", value = "名字的关键字", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "gradeName",value ="年级",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "className",value ="班级",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String")
    })
    @GetMapping("/page")
    public Result page(String studentName,String gradeName,String className){
        studentName=Convert.toStr(studentName,"");
        Page<StudentModel> page=getPage();
        IPage<StudentModel> iPage= studentService.page(page, studentName, gradeName, className);
        return ResultUtil.success(iPage);
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
     * 后台系统更新学生
     */
    @SysLog(prefix = "后台系统更新学生")
    @ApiOperation("后台系统更新学生")
    @ApiImplicitParam(name = "student",value = "要更新的学生",dataType = "StudentWrapper")
    @PostMapping("/updateStudentBackstage")
    public Result updateStudentBackstage(@RequestBody StudentWrapper student){
        return studentService.updateStudentBackstage(student);
    }

    /**
     * 账号系统更新学生
     */
    @SysLog(prefix = "账号系统更新学生")
    @ApiOperation("账号系统更新学生")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "studentId",value = "要更新的学生",paramType = "query",dataType = "String"),
        @ApiImplicitParam(name = "studentName",value = "更新后学生的名字",paramType = "query",dataType ="String"),
        @ApiImplicitParam(name = "oldPassword",value = "旧密码",paramType = "query",dataType = "String"),
        @ApiImplicitParam(name = "newPassword",value = "新密码",paramType = "query",dataType = "String")
    })

    @PostMapping("/updateStudentAccount")
    public Result updateStudentAccount(@RequestParam(value = "studentId",required = true) String studentId,
                                       @RequestParam(value = "studentName",required = true) String studentName,
                                       @RequestParam(value = "oldPassword") String oldPassword,
                                       @RequestParam(value = "newPassword") String newPassword,
                                       @RequestParam(value = "file") MultipartFile file){
        return studentService.updateStudentAccount(studentId,studentName,oldPassword,newPassword,file);
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

    /**
     * 从模板中添加学生
     */
    @SysLog(prefix = "从模板中添加学生")
    @ApiOperation("从模板中添加学生")
    @PostMapping("/addByFile")
    public Result addByFile(@RequestParam(value = "file",required = true)MultipartFile file){
        return studentService.addStudentByTemplate(file,studentService);
    }
}