package hzau.sa.backstage.controller;

import cn.hutool.core.convert.Convert;
import hzau.sa.backstage.entity.MeasureWrapper;
import hzau.sa.backstage.service.impl.MeasureServiceImpl;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import hzau.sa.backstage.entity.MeasureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.service.MeasureService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;


/**
 *  控制器
 * @author wuyihu
 * @date 2020-08-12
 */
@Slf4j
@RestController
@RequestMapping("/measure")
@Api(value = "-API", tags = { "措施接口" })
public class MeasureController extends BaseController {

    @Autowired
    private MeasureServiceImpl measureService;

    @SysLog(prefix = "增加措施")
    @ApiOperation("增加措施")
    @ApiImplicitParam(name = "measure",value = "增加的措施",paramType = "body",dataType = "MeasureWrapper")
    @PostMapping("/addMeasure")
    public Result addMeasure(@RequestBody MeasureWrapper measure){return measureService.addMeasure(measure);}

    @SysLog(prefix = "删除措施")
    @ApiOperation("删除措施")
    @ApiImplicitParam(name = "measureId",value = "删除措施的id",paramType = "query",dataType = "String")
    @PostMapping("/deleteMeasure")
    public Result deleteMeasure(String measureId){
        return measureService.deleteMeasure(Integer.valueOf(measureId));
    }

    @SysLog(prefix = "批量删除措施")
    @ApiOperation("批量删除措施")
    @ApiImplicitParam(name = "measureIds",value = "批量删除措施的id",paramType = "query",allowMultiple = true,dataType = "Integer")
    @PostMapping("/deleteMeasures")
    public Result deleteMeasures(Integer[] measureIds){return measureService.deleteMeasures(measureIds);}

    @SysLog(prefix = "更新措施")
    @ApiOperation("更新措施")
    @ApiImplicitParam(name = "measure",value = "更新措施",paramType = "body",dataType = "MeasureWrapper")
    @PostMapping("/updateMeasure")
    public Result updateMeasure(@RequestBody MeasureWrapper measure){
        return measureService.updateMesure(measure);
    }

    @ApiOperation(value = "按名字分页模糊查询", notes = "按名字分页模糊查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String"),

    })
    @GetMapping("/page")
    public Result page(String keyword){
        keyword = Convert.toStr(keyword,"");
        Page<MeasureVO> page = getPage();
        log.info(keyword);
        log.info(page.toString());
        QueryWrapper<MeasureVO> queryWrapper = new QueryWrapper<MeasureVO>();
        queryWrapper.like("measureName",keyword)
                .orderByAsc("createTime");
        return ResultUtil.success(measureService.page(page,queryWrapper));
    }
}