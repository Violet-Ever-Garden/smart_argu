package hzau.sa.backstage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.dao.ClassDao;
import hzau.sa.backstage.dao.GradeDao;
import hzau.sa.backstage.entity.*;
import hzau.sa.backstage.service.impl.StudentServiceImpl;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import hzau.sa.backstage.service.StudentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.web.multipart.MultipartFile;


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

    @Autowired
    private ClassDao classDao;

    @Autowired
    private GradeDao gradeDao;

    /**
     * 按名字分页模糊查询
     * @param keyword
     * @return
     */
    @ApiOperation(value = "按名字分页模糊查询", notes = "按名字分页模糊查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String")

    })
    @GetMapping("/pageByName")
    public Result pageByName(String keyword){
        keyword = Convert.toStr(keyword,"");
        Page<StudentVO> page = getPage();
        log.info(keyword);
        log.info(page.toString());
        QueryWrapper<StudentVO> queryWrapper = new QueryWrapper<StudentVO>();

        queryWrapper.like("studentName",keyword)
                .orderByAsc("createTime");
        return ResultUtil.success(studentService.page(page,queryWrapper));
    }

    /**
     * 按年级分页查询
     */
    @ApiOperation(value = "按年级分页查询", notes = "按年级分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "gradeName",value = "年级名",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String")

    })
    @GetMapping("/pageByGrade")
    public Result pageByGrade(String keyword,String gradeName){
        keyword = Convert.toStr(keyword,"");
        Page<StudentVO> page = getPage();
        log.info(keyword);
        log.info(page.toString());
        QueryWrapper<StudentVO> queryWrapper = new QueryWrapper<StudentVO>();

        QueryWrapper<GradeVO> gradeVOQueryWrapper=new QueryWrapper<>();
        gradeVOQueryWrapper.eq("gradeName",gradeName);


        queryWrapper.eq("gradeId",gradeDao.selectOne(gradeVOQueryWrapper).getGradeId())
                .orderByAsc("createTime");
        return ResultUtil.success(studentService.page(page,queryWrapper));
    }


    /**
     * 按班级分页查询
     * @param keyword
     * @param className
     * @return
     */
    @ApiOperation(value = "按班级分页查询", notes = "按班级分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "className",value = "班级名",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String")

    })
    @GetMapping("/pageByClass")
    public Result pageByClass(String keyword,String className){
        keyword = Convert.toStr(keyword,"");
        Page<StudentVO> page = getPage();
        log.info(keyword);
        log.info(page.toString());
        QueryWrapper<StudentVO> queryWrapper = new QueryWrapper<StudentVO>();

        QueryWrapper<ClassVO> classVOQueryWrapper=new QueryWrapper<>();
        classVOQueryWrapper.eq("className",className);


        queryWrapper.eq("classId",classDao.selectOne(classVOQueryWrapper).getClassId())
                .orderByAsc("createTime");
        return ResultUtil.success(studentService.page(page,queryWrapper));
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
     * 更新学生
     */
    @SysLog(prefix = "更新学生")
    @ApiOperation("更新学生")
    @ApiImplicitParam(name = "student",value = "要更新的学生",dataType = "StudentWrapper")
    @PostMapping("/updateStudent")
    public Result updateStudent(@RequestBody StudentWrapper student,@RequestParam(value = "file") MultipartFile file){
        return studentService.updateStudent(student,file);
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