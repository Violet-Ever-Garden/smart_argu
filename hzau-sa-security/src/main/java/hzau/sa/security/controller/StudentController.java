package hzau.sa.security.controller;

import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.CodeType;
import hzau.sa.msg.util.ResultUtil;
import hzau.sa.security.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.security.entity.StudentVO;

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
@RequestMapping("sys/student")
@Api(value = "-API", tags = { "相关接口" })
public class StudentController extends BaseController {

    @Autowired
    private StudentServiceImpl studentService;

    /**
     * 分页列表
     */
    @ApiOperation("分页查询")
    @ApiImplicitParam(name = "student", value = "实体", paramType = "query", dataType = "StudentVO")
    @GetMapping("/page")
    public Result page(StudentVO student) {
        Page<StudentVO> page = getPage();
        QueryWrapper<StudentVO> queryWrapper = new QueryWrapper<StudentVO>(student);
        IPage<StudentVO> pageInfo = studentService.page(page, queryWrapper);
        HashMap result = new HashMap();
        result.put("total", pageInfo.getTotal());
        result.put("rows", pageInfo.getRecords());
        return ResultUtil.selfResult(CodeType.SUCCESS.getCode(),CodeType.SUCCESS.getMsg(),result);
    }

    //TODO.....
}