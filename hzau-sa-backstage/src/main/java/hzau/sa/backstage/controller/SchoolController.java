package hzau.sa.backstage.controller;

import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;

import cn.hutool.core.convert.Convert;
import hzau.sa.backstage.entity.FieldModel;
import hzau.sa.backstage.service.impl.SchoolServiceImpl;
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
import hzau.sa.backstage.entity.SchoolVO;
import hzau.sa.backstage.service.SchoolService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;


/**
 *  控制器
 * @author wyh
 * @date 2020-08-25
 */
@Slf4j
@RestController
@RequestMapping("/school")
@Api(value = "-API", tags = { "学校接口" })
public class SchoolController extends BaseController {

    @Autowired
    private SchoolServiceImpl schoolService;

    @SysLog(prefix ="增加学校" )
    @ApiOperation("增加学校")
    @ApiImplicitParam(name = "schoolVO", value = "实体", paramType = "body", dataType = "SchoolVO")
    @PostMapping("/addSchool")
    public Result addSchool(@RequestBody SchoolVO schoolVO){
        return schoolService.addSchool(schoolVO);
    }

    @SysLog(prefix ="删除学校" )
    @ApiOperation("删除学校")
    @ApiImplicitParam(name = "schoolId", value = "学校id", paramType = "query", dataType = "String")
    @PostMapping("/deleteSchool")
    public Result deleteSchool(String schoolId){
        return schoolService.deleteSchool(Integer.valueOf(schoolId));
    }

    @SysLog(prefix ="批量删除学校" )
    @ApiOperation("批量删除学校")
    @ApiImplicitParam(name = "schoolIds", value = "学校id集合", paramType = "query",allowMultiple = true,dataType = "Integer")
    @PostMapping("/deleteSchools")
    public Result deleteSchools(Integer[] schoolIds){
        return schoolService.deleteSchools(schoolIds);
    }

    @SysLog(prefix ="修改学校" )
    @ApiOperation("修改学校")
    @ApiImplicitParam(name = "schoolVO", value = "实体", paramType = "body", dataType = "SchoolVO")
    @PostMapping("/updateSchool")
    public Result updateSchool(@RequestBody SchoolVO schoolVO){
        return schoolService.updateSchool(schoolVO);
    }

    @SysLog(prefix = "按名字分页模糊查询")
    @ApiOperation(value = "按名字分页模糊查询", notes = "按名字分页模糊查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "schoolName", value = "学校关键字", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String"),

    })
    @GetMapping("/page")
    public Result page(String schoolName){
        schoolName= Convert.toStr(schoolName,"");
        Page<SchoolVO> page = getPage();
        QueryWrapper<SchoolVO> schoolVOQueryWrapper = new QueryWrapper<>();
        schoolVOQueryWrapper.lambda().like(SchoolVO::getSchoolName,schoolName)
                .orderByDesc(SchoolVO::getCreateTime);
        IPage<SchoolVO> iPage = schoolService.page(page,schoolVOQueryWrapper);
        return ResultUtil.success(iPage);
    }

    @SysLog(prefix = "学校模板下载")
    @ApiOperation(value = "学校模板下载",notes = "学校模板下载")
    @GetMapping("/templateDownload")
    public Result templateDownload(){
        return schoolService.templateDownload();
    }


}