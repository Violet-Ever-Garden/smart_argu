package hzau.sa.backstage.controller;

import hzau.sa.msg.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.Base;
import hzau.sa.backstage.service.BaseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;


/**
 *  控制器
 * @author lvhao
 * @date 2020-08-26
 */
@RestController
@RequestMapping("sys/base")
@Api(value = "-API", tags = { "相关接口" })
public class BaseVOController extends BaseController {

    @Autowired
    private BaseService baseService;


//    @ApiOperation("分页查询")
//    @ApiImplicitParam(name = "base", value = "实体", paramType = "query", dataType = "BaseVO")
//    @GetMapping("/page")
}