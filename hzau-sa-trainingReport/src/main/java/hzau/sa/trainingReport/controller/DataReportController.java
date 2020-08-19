package hzau.sa.trainingReport.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import hzau.sa.trainingReport.dao.DataReportRepository;
import hzau.sa.trainingReport.entity.AnalysisModel;
import hzau.sa.trainingReport.entity.DataReport;
import hzau.sa.trainingReport.entity.DataReportModel;
import hzau.sa.trainingReport.service.impl.DataReportServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 调查数据相关接口
 * @author Hasee
 */
@Slf4j
@RestController
@RequestMapping("/dataReport")
@Api(value = "调查数据-API",tags = "调查数据相关接口")
public class DataReportController extends BaseController {
    @Autowired
    DataReportRepository dataReportRepository;
    @Autowired
    DataReportServiceImpl dataReportService;


    @ApiOperation(value = "获取数据列表", notes = "获取数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "cropId",value = "作物id",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "studentId",value = "学生id",paramType = "query",dataType = "String")
    })
    @GetMapping("/page")
    public Result page(int cropId , String studentId){
        Page<DataReportModel> page = getPage();

        List<DataReportModel> dataReportModels = dataReportService.selectDataReportModelPage(page, cropId, studentId);
        return ResultUtil.success(dataReportModels);
    }


    @ApiOperation(value = "获取数据", notes = "获取数据")
    @ApiImplicitParam(name = "dataReportId", value = "id", paramType = "path", dataType = "String")
    @GetMapping("/get/{dataReportId}")
    public Result getList(@PathVariable int dataReportId){
        DataReport dataReport = dataReportRepository.findByDataReportId(dataReportId);
        return ResultUtil.success(dataReport);
    }

    @ApiOperation(value = "保存数据表数据", notes = "保存数据表数据")
    @ApiImplicitParam(name = "dataReport", value = "数据报表实体",paramType = "body", dataType = "DataReport")
    @PostMapping("/save")
    public Result<Object> save(@RequestBody DataReport dataReport){
        System.out.println(dataReport);
        boolean insert = dataReportService.insert(dataReport);
        if(false==insert){
            return ResultUtil.databaseError("新增失败");
        }else{
            return ResultUtil.success("新增成功");
        }
    }

    @ApiOperation(value = "更新数据表数据", notes = "更新数据表数据")
    @ApiImplicitParam(name = "dataReport", value = "数据报表实体",paramType = "body", dataType = "DataReport")
    @PostMapping("/update")
    public Result<Object> updateDataReport(@RequestBody DataReport dataReport){
        boolean b = dataReportService.updateDataReport(dataReport);
        if(false==b){
            return ResultUtil.databaseError("更新失败");
        }else{
            return ResultUtil.success("更新成功");
        }
    }


    @ApiOperation(value = "更新数据表数据", notes = "更新数据表数据")
    @ApiImplicitParam(name = "dataReportId", value = "id", paramType = "path", dataType = "String")
    @PostMapping("/delete/{dataReportId}")
    public Result<Object> delete(@PathVariable  int dataReportId){
        dataReportService.deleteByDataReportId(dataReportId);
        return ResultUtil.success();
    }




    @ApiOperation(value = "统计分析", notes = "统计分析")
    @ApiImplicitParam(name = "ids[]", value = "作物id数组", paramType = "query", allowMultiple = true, dataType = "String")
    @GetMapping("/statisticalAnalysis")
    public Result statisticalAnalysis(@RequestParam(value = "ids[]") ArrayList<Integer> ids){
        List<AnalysisModel> analysisModels = dataReportService.getStatisticalAnalysis(ids);
        return ResultUtil.success(analysisModels);
    }
}
