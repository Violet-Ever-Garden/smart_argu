package hzau.sa.backstage.controller;

import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;

import cn.hutool.core.convert.Convert;
import hzau.sa.backstage.entity.CropModel;
import hzau.sa.backstage.entity.MeasureVO;
import hzau.sa.backstage.entity.TeacherWrapper;
import hzau.sa.backstage.service.impl.TeacherServiceImpl;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
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
     * 更新老师
     */
    @SysLog(prefix = "更新老师")
    @ApiOperation("更新老师")
    @ApiImplicitParam(name = "teacherWrapper",value = "更新的老师",paramType = "body",dataType = "TeacherWrapper")
    @PostMapping("/updateTeacher")
    public Result updateTeacher(@RequestBody TeacherWrapper teacherWrapper,@RequestParam(value = "file")MultipartFile file){
        return teacherService.updateTeacher(teacherWrapper,file);
    }
}