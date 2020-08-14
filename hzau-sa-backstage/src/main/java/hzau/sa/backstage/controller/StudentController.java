package hzau.sa.backstage.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.baomidou.mybatisplus.extension.api.R;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.entity.Result;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

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
     * 返回所有年级
     */
    @SysLog(prefix = "返回所有年级")
    @ApiOperation("返回所有年级")
    @PostMapping("/getAllGrades")
    public Result getAllGrades(){
        return studentService.getAllGrades();
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
    public Result pageByGrade(int gradeId,int pageNo){
        return studentService.pageByGrade(gradeId,pageNo);
    }

    /**
     * 返回所有班级
     */
    @SysLog(prefix = "返回所有班级")
    @ApiOperation("返回所有班级")
    @PostMapping("/getAllClasses")
    public Result getAllClasses(){
        return studentService.gerAllClasses();
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
    public Result pageByClass(int classId,int pageNo){
        return studentService.pageByClasses(classId,pageNo);
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
    @ApiImplicitParam(name = "student",value = "要添加的学生",paramType ="body",dataType = "StudentVO")
    @PostMapping("/addStudent")
    public Result addStudent(@RequestBody StudentVO student){
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
    @ApiImplicitParam(name = "student",value = "要更新的学生",dataType = "StudentVO")
    @PostMapping("/updateStudent")
    public Result updateStudent(@RequestBody StudentVO studentVO){
        return studentService.updateStudent(studentVO);
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