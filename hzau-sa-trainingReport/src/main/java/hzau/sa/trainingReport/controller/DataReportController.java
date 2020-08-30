package hzau.sa.trainingReport.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import hzau.sa.msg.util.ZipUtil;
import hzau.sa.trainingReport.dao.DataReportRepository;
import hzau.sa.trainingReport.entity.CropDataReport;
import hzau.sa.trainingReport.entity.DataReport;
import hzau.sa.trainingReport.entity.DataReportModel;
import hzau.sa.trainingReport.entity.StudentReportModel;
import hzau.sa.trainingReport.service.impl.DataReportServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        IPage<DataReportModel> dataReportModels = dataReportService.selectDataReportModelPage(page, cropId, studentId);
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


    @ApiOperation(value = "删除数据表数据", notes = "删除数据表数据")
    @ApiImplicitParam(name = "dataReportId", value = "调查数据id", paramType = "path", dataType = "String")
    @PostMapping("/delete/{dataReportId}")
    public Result<Object> delete(@PathVariable  int dataReportId){
        dataReportService.deleteByDataReportId(dataReportId);
        return ResultUtil.success();
    }



    @ApiOperation(value = "批量删除数据表数据", notes = "批量删除数据表数据")
    @ApiImplicitParam(name = "ids", value = "调查数据id", paramType = "query", allowMultiple = true, dataType = "String")
    @GetMapping("/deleteByIds")
    public Result deleteByIds(@RequestParam(value = "ids") ArrayList<Integer> ids){
        log.info(ids.toString());
        return dataReportService.deleteDataReportsByDataReportIdIn(ids);
    }


    @ApiOperation(value = "统计分析", notes = "统计分析")
    @ApiImplicitParam(name = "ids", value = "作物id数组", paramType = "query", allowMultiple = true, dataType = "String")
    @GetMapping("/statisticalAnalysis")
    public Result statisticalAnalysis(@RequestParam(value = "ids") ArrayList<Integer> ids){
        log.info(ids.toString());
        List<CropDataReport> statisticalAnalysis = dataReportService.getStatisticalAnalysis(ids);
        return ResultUtil.success(statisticalAnalysis);
    }


    @ApiOperation(value = "按班级id与作物id搜索存在调查报告的同学模板", notes = "最新提交时间是调查数据的最新时间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "cropId",value = "作物id",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "classId",value = "班级id",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "studentName",value = "学生名称",paramType = "query",dataType = "String")
    })
    @GetMapping("/getStudentByCropAndClass")
    public Result getStudentByCropAndClass(int classId,int cropId,String studentName){
        studentName = Convert.toStr(studentName,"");
        Page<StudentReportModel> page= getPage();
        IPage<StudentReportModel> studentReportModelIPage = dataReportService.selectStudentByClassAndCrop(page,classId,cropId,studentName);
        return ResultUtil.success(studentReportModelIPage);
    }

    @ApiOperation("老师导出调查数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cropId",value = "作物Id",required = true,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "classIds",value = "班级Id数组",required = true,paramType = "query",allowMultiple = true,dataType = "String"),
            @ApiImplicitParam(name = "teacherId",value = "老师工号",required = true,paramType = "query",dataType = "String")
    })
    @GetMapping("/exportReportTeacher")
    public void exportReportTeacher(HttpServletResponse httpServletResponse,
                             int cropId, @RequestParam("classIds") ArrayList<Integer> classIds, String teacherId){

        String fileDir = null;
        try{
            fileDir = dataReportService.excelDirByTeacher(classIds,cropId,teacherId);

            if(new File(fileDir).exists()){

                httpServletResponse.setContentType("application/octet-stream");
                httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + teacherId + "report" + ".zip");

                ZipUtil.zipCompress(fileDir,httpServletResponse.getOutputStream());
            }
        }catch (Exception e){
            log.error(e.toString());
        }
    }



    @ApiOperation("学生导出调查数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cropId",value = "作物Id",required = true,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "studentId",value = "学生id",required = true,paramType = "query",dataType = "String")
    })
    @GetMapping("/exportReportStudent")
    public void exportReportStudent(HttpServletResponse httpServletResponse,
                                    int cropId, String studentId) throws IOException {
        dataReportService.excelDirByStudent(studentId,cropId,httpServletResponse);
    }
}
