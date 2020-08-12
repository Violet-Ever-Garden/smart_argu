package hzau.sa.security.controller;

import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.CodeType;
import hzau.sa.msg.util.ResultUtil;
import hzau.sa.security.service.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.security.entity.TeacherVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;

import java.util.HashMap;


/**
 *  控制器
 * @author haokai
 * @date 2020-08-10
 */
@RestController
@RequestMapping("sys/teacher")
@Api(value = "-API", tags = { "相关接口" })
public class TeacherController extends BaseController {

    @Autowired
    private TeacherServiceImpl teacherService;

    /**
     * 分页列表
     */
    @ApiOperation("分页查询")
    @ApiImplicitParam(name = "teacher", value = "实体", paramType = "query", dataType = "TeacherVO")
    @GetMapping("/page")
    public Result page(TeacherVO teacher) {
        Page<TeacherVO> page = getPage();
        QueryWrapper<TeacherVO> queryWrapper = new QueryWrapper<TeacherVO>(teacher);
        IPage<TeacherVO> pageInfo = teacherService.page(page, queryWrapper);
        HashMap result = new HashMap();
        result.put("total", pageInfo.getTotal());
        result.put("rows", pageInfo.getRecords());
        return ResultUtil.selfResult(CodeType.SUCCESS.getCode(),CodeType.SUCCESS.getMsg(),result);
    }

    //TODO.....
}