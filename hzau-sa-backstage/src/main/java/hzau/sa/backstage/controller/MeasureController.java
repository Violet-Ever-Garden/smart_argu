package hzau.sa.backstage.controller;

import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;

import hzau.sa.backstage.entity.FieldVO;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.entity.Result;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import hzau.sa.backstage.entity.MeasureVO;
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
@RequestMapping("/sys/measure")
@Api(value = "-API", tags = { "措施接口" })
public class MeasureController{

    @Autowired
    private MeasureService measureService;

    @SysLog(prefix = "分页查询措施")
    @ApiOperation("分页查询措施")
    @ApiImplicitParam(name = "pageNo", value = "请求的页数", paramType = "query", dataType = "String")
    @PostMapping("/page")
    public Result page(String pageNo) {
        return measureService.page(Integer.parseInt(pageNo));
    }


    @SysLog(prefix = "增加措施")
    @ApiOperation("增加措施")
    @ApiImplicitParam(name = "measure",value = "增加的措施",paramType = "body",dataType = "MeasureVO")
    @PostMapping("/addMeasure")
    public Result addMeasure(@RequestBody MeasureVO measure){return measureService.addMeasure(measure);}

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
    @ApiImplicitParam(name = "measure",value = "更新措施",paramType = "body",dataType = "MeasureVO")
    @PostMapping("/updateMeasure")
    public Result updateMeasure(@RequestBody MeasureVO measure){
        return measureService.updateMesure(measure);
    }

    @SysLog(prefix = "查找措施")
    @ApiOperation("查找措施")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "measureName",value = "查找措施的名字",paramType = "query",dataType = "String"),
    @ApiImplicitParam(name = "pageNo",value = "即将显示的页数",paramType = "query",dataType = "String")
    })
    @PostMapping("/findMeasure")
    public Result findMeasure(String measureName,String pageNo){
        return measureService.findMeasure(measureName, Integer.parseInt(pageNo));
    }
}