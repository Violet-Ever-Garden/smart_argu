package hzau.sa.trainingReport.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.FileEnum;
import hzau.sa.msg.util.ResultUtil;
import hzau.sa.msg.util.ZipUtil;
import hzau.sa.trainingReport.entity.TrainingReportPageWithoutFile;
import hzau.sa.trainingReport.service.TrainingReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author LvHao
 * @Description : 实训报告操作的控制器
 * @date 2020-08-20 15:32
 */
@Slf4j
@RestController
@RequestMapping("/trainingReport")
@Api(value = "实训报告操作-API接口",tags = "实训报告操作相关接口")
public class TrainingReportController extends BaseController {

    @Resource
    private TrainingReportService trainingReportService;


    @SysLog(prefix = "导出实训报告")
    @ApiOperation("导出实训报告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cropId",value = "作物Id",required = true,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "classIds",value = "班级Id数组",required = true,paramType = "query",allowMultiple = true,dataType = "String"),
            @ApiImplicitParam(name = "teacherId",value = "老师工号",required = true,paramType = "query",dataType = "String")
    })
    @GetMapping("/exportReport")
    public void exportReport(HttpServletResponse httpServletResponse,
                             String cropId, @RequestParam("classIds") String[] classIds, String teacherId){

        String fileDir = null;
        try{
            fileDir = trainingReportService.excelDir(cropId,classIds,teacherId);

            if(new File(fileDir).exists()){

                httpServletResponse.setContentType("application/octet-stream");
                httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + teacherId + "report" + ".zip");

                ZipUtil.zipCompress(fileDir,httpServletResponse.getOutputStream());
            }
        }catch (Exception e){
            log.error(e.toString());
        }
    }

    @SysLog(prefix = "增加实训报告")
    @ApiOperation("增加实训报告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId",value = "学生的id",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "cropId",value = "作物id",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "trainingReportName",value = "实训报告名称",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "batch",value = "批次",paramType = "query",required = true,dataType = "String")
    })
    @PostMapping("/addTrainingReport")
    public Result addTrainingReport(String studentId, String cropId, String trainingReportName, String batch, @RequestParam(value="file",required = true) MultipartFile file){
        return trainingReportService.addTrainingReport(studentId, Integer.valueOf(cropId),trainingReportName,Integer.valueOf(batch),file);
    }


    @SysLog(prefix = "删除实训报告")
    @ApiOperation("删除实训报告")
    @ApiImplicitParam(name ="trainingReportId",value = "删除的实训报告的id",required = true,paramType = "query",dataType = "String")
    @PostMapping("/deleteTrainingReport")
    public Result deleteTrainingReport(String trainingReportId){
        return trainingReportService.deleteTrainingReport(Integer.valueOf(trainingReportId));
    }

    @SysLog(prefix = "批量删除实训报告")
    @ApiOperation("批量删除实训报告")
    @ApiImplicitParam(name = "trainingReportIds",value = "批量删除的id集合",required = true,allowMultiple = true,paramType = "query",dataType = "Integer")
    @PostMapping("/deleteTrainingReports")
    public Result deleteTrainingReports(Integer[] trainingReportIds){
        return trainingReportService.deleteTrainingReports(trainingReportIds);
    }

    @SysLog(prefix = "学生修改实训报告")
    @ApiOperation("学生修改实训报告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "trainingReportId",value = "实训报告id",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "cropId",value = "作物id",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "studentId",value = "学生",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "trainingReportName",value = "实训报告名称",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "batch",value = "批次",paramType = "query",dataType = "String")
    })
    @PostMapping("/changeReportByStudent")
    public Result changeReportByStudent(String trainingReportId,String cropId,String studentId,String trainingReportName,String batch,@RequestParam(value = "file",required = true) MultipartFile file){
       return trainingReportService.updateTrainingReportByStudent(Integer.valueOf(trainingReportId), Integer.valueOf(cropId),studentId,trainingReportName, Integer.valueOf(batch),file);
    }

    @SysLog(prefix = "老师修改实训报告")
    @ApiOperation("老师修改实训报告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "trainingReportId",value = "实训报告id",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "comments",value = "评语",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "score",value = "得分",paramType = "query",dataType = "String")
    })
    @PostMapping("/changeReportByTeacher")
    public Result changeReportByTeacher(String trainingReportId,String comments,String score){
        return trainingReportService.updateTrainingReportByTeacher(Integer.valueOf(trainingReportId),comments,Integer.valueOf(score));
    }

    @SysLog(prefix = "实训报告分页查询")
    @ApiOperation("实训报告分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cropId", value = "作物id", required = true,paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "studentId", value = "学生id",required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "reviewStatus", value = "评阅状态", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "trainingReportName", value = "实训报告关键字", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page", value = "页数（默认1 可为null）", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "limit", value = "容量（默认20 可为null）", paramType = "query", dataType = "String")
    }
    )
    @GetMapping("/page")
    public Result page(String cropId,String studentId,String startTime,String endTime,String reviewStatus,String trainingReportName){
        trainingReportName=Convert.toStr(trainingReportName,"");
        Page<TrainingReportPageWithoutFile> page = getPage();
        IPage<TrainingReportPageWithoutFile> iPage = trainingReportService.page(page, Integer.valueOf(cropId), studentId, startTime, endTime, reviewStatus, trainingReportName);
        return ResultUtil.success(iPage);
    }

    @SysLog(prefix = "学生视角下的按实训报告id返回实训报告模型")
    @ApiOperation("学生视角下的按实训报告id返回实训报告模型")
    @ApiImplicitParam(name = "trainingReportId",value = "实训报告id",paramType = "query",dataType ="String",required = true)
    @GetMapping("/studentView")
    public Result studentView(String trainingReportId){
        return trainingReportService.studentView(Integer.valueOf(trainingReportId), String.valueOf(FileEnum.TRAININGREPORT));
    }

    @SysLog(prefix = "老师视角下的按实训报告id返回实训报告模型")
    @ApiOperation("老师视角下的按实训报告id返回实训报告模型")
    @ApiImplicitParam(name = "trainingReportId",value = "实训报告id",paramType = "query",dataType ="String",required = true)
    @GetMapping("/teachertView")
    public Result teacherView(String trainingReportId){
        return trainingReportService.teacherView(Integer.valueOf(trainingReportId), String.valueOf(FileEnum.TRAININGREPORT));
    }
}
