package hzau.sa.backstage.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.TeacherVO;
import hzau.sa.backstage.entity.TeacherWrapper;
import hzau.sa.backstage.service.impl.TeacherServiceImpl;
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


/**
 *  控制器
 * @author wuyihu
 * @date 2020-08-13
 */
@Slf4j
@RestController
@RequestMapping("/teacher")
@Api(value = "-API", tags = { "老师接口" })
public class TeacherController extends BaseController {

    @Autowired
    private TeacherServiceImpl teacherService;


    @ApiOperation(value = "按名字分页模糊查询无图片", notes = "按名字分页模糊查询无图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String"),

    })
    @GetMapping("/page")
    public Result page(String keyword){
        keyword = Convert.toStr(keyword,"");
        Page<TeacherVO> page = getPage();
        log.info(keyword);
        log.info(page.toString());
        QueryWrapper<TeacherVO> queryWrapper = new QueryWrapper<TeacherVO>();
        queryWrapper.like("teacherName",keyword)
                .orderByAsc("createTime");

        return ResultUtil.success(teacherService.page(page,queryWrapper));
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
     * 后台系统更新老师
     */
    @SysLog(prefix = "后台系统更新老师")
    @ApiOperation("后台系统更新老师")
    @ApiImplicitParam(name = "teacherWrapper",value = "更新的老师",paramType = "body",dataType = "TeacherWrapper")
    @PostMapping("/updateTeacherBackstage")
    public Result updateTeacherBackstage(@RequestBody TeacherWrapper teacherWrapper){
        return teacherService.updateTeacherBackstage(teacherWrapper);
    }

    /**
     * 账号系统更新学生
     */
    @SysLog(prefix = "账号系统更新老师")
    @ApiOperation("账号系统更新老师")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId",value = "要更新的老师",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "teacherName",value = "更新后老师的名字",paramType = "query",dataType ="String"),
            @ApiImplicitParam(name = "oldPassword",value = "旧密码",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "newPassword",value = "新密码",paramType = "query",dataType = "String")
    })

    @PostMapping("/updateTeacherAccount")
    public Result updateTeacherAccount(@RequestParam(value = "teacherId",required = true) String teacherId,
                                       @RequestParam(value = "teacherName",required = true) String teacherName,
                                       @RequestParam(value = "oldPassword") String oldPassword,
                                       @RequestParam(value = "newPassword") String newPassword,
                                       @RequestParam(value = "file") MultipartFile file){
        return teacherService.updateTeacherAccount(teacherId,teacherName,oldPassword,newPassword,file);
    }
}