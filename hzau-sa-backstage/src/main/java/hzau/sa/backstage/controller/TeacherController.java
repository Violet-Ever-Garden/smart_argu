package hzau.sa.backstage.controller;

import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;

import hzau.sa.backstage.entity.TeacherWrapper;
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
import hzau.sa.backstage.entity.TeacherVO;
import hzau.sa.backstage.service.TeacherService;

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
@RequestMapping("sys/teacher")
@Api(value = "-API", tags = { "老师接口" })
public class TeacherController{

    @Autowired
    private TeacherService teacherService;

    /**
     * 分页显示老师
     */
    @SysLog(prefix = "分页显示老师")
    @ApiOperation("分页显示老师")
    @ApiImplicitParam(name = "pageNo",value = "要显示的页数",paramType = "query",dataType = "String")
    @PostMapping("/page")
    public Result page(String pageNo){
        return teacherService.page(Integer.parseInt(pageNo));
    }
    /**
     * 按名字分页老师
     */
    @SysLog(prefix = "按名字分页老师")
    @ApiOperation("按名字分页老师")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "name",value = "老师的名字",paramType = "query",dataType = "String"),
    @ApiImplicitParam(name="pageNo",value = "要显示的页数",paramType = "query",dataType = "String")
    })
    @PostMapping("/pageByName")
    public Result pageByName(String name,String pageNo){
        return teacherService.pageByName(name, Integer.parseInt(pageNo));
    }



    /**
     * 增加老师
     */
    @SysLog(prefix = "增加老师")
    @ApiOperation("增加老师")
    @ApiImplicitParam(name = "teacherWrapper",value = "要插入的老师包装",paramType = "body",dataType = "TeacherWrapper")
    @PostMapping("/addTeacher")
    public Result addTeacher(@RequestBody TeacherWrapper teacherWrapper){
        return teacherService.addTeacher(teacherWrapper);
    }



    /**
     * 删除老师
     */

    @SysLog(prefix = "删除老师")
    @ApiOperation("删除老师")
    @ApiImplicitParam(name = "teacherId",value = "删除老师的id",paramType = "query",dataType = "String")
    @PostMapping("/deleteTeacher")
    public Result deleteTeacher(String teacherId){
        return teacherService.deleteTeacher(teacherId);
    }


    /**
     * 批量删除老师
     */


    @SysLog(prefix = "批量删除老师")
    @ApiOperation("批量删除老师")
    @ApiImplicitParam(name = "teacherIds",value = "批量删除老师的id",paramType = "query",allowMultiple = true,dataType = "String")
    @PostMapping("/deleteTeachers")
    public Result deleteTeacher(String[] teacherIds){
        return teacherService.deleteTeachers(teacherIds);
    }


    /**
     * 更新老师
     */
    @SysLog(prefix = "更新老师")
    @ApiOperation("更新老师")
    @ApiImplicitParam(name = "teacherWrapper",value = "更新的老师",paramType = "body",dataType = "TeacherWrapper")
    @PostMapping("/updateTeacher")
    public Result updateTeacher(@RequestBody TeacherWrapper teacherWrapper){
        return teacherService.updateTeacher(teacherWrapper);
    }
}