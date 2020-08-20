package hzau.sa.trainingReport.controller;

import hzau.sa.msg.util.ZipUtil;
import hzau.sa.trainingReport.service.TrainingReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LvHao
 * @Description :
 * @date 2020-08-20 15:32
 */
@Slf4j
@RestController
@RequestMapping("/trainingReport")
@Api(value = "实训报告操作-API接口",tags = "实训报告操作相关接口")
public class TrainingReportController {

    @Resource
    private TrainingReportService trainingReportService;


    @ApiOperation("导出实训报告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cropId",value = "作物Id",required = true,paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "classIds[]",value = "班级Id数组",required = true,paramType = "query",allowMultiple = true,dataType = "String"),
            @ApiImplicitParam(name = "teacherId",value = "老师工号",required = true,paramType = "query",dataType = "String")
    })
    @GetMapping("/exportReport")
    public void exportReport(HttpServletResponse httpServletResponse,
                             String cropId, @RequestParam("classIds[]") String[] classIds, String teacherId){

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

}
