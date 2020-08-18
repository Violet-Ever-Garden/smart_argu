package hzau.sa.trainingReport.controller;

import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.LogType;
import hzau.sa.msg.util.ResultUtil;
import hzau.sa.trainingReport.entity.MeasureManageRequest;
import hzau.sa.trainingReport.entity.MeasureManageResponse;
import hzau.sa.trainingReport.service.MeasuremanageService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author LvHao
 * @Description :
 * @date 2020-08-18 10:42
 */
@Slf4j
@RestController
@RequestMapping("/measuremanage")
@Api(value = "措施管理-API接口",tags = "措施管理相关接口")
public class MeasuremanageController {

    @Resource
    private MeasuremanageService measuremanageService;

    @SysLog(prefix = "新增措施信息",value = LogType.ALL)
    @ApiOperation("新增措施信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId",value = "学号",required = true,paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "crop",value = "作物名称",required = true,paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "measure",value = "措施名称",required = true,paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "measureContent",value = "措施内容",required = true,paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "createTime",value = "创建时间",required = true,paramType = "form",dataType = "String")
    })
    @PostMapping("/insert")
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> insertMeasure(String studentId,
                                        String crop,
                                        String measure,
                                        String measureContent,
                                        String createTime,
                                        @ApiParam(name = "files",value = "图片数组") MultipartFile[] files){
        Object savePoint = null;
        boolean flag = false;
        try{
            savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();

            MeasureManageRequest measureManageRequest = new MeasureManageRequest();
            measureManageRequest.setStudentId(studentId);
            measureManageRequest.setCrop(crop);
            measureManageRequest.setMeasure(measure);
            measureManageRequest.setMeasureContent(measureContent);
            measureManageRequest.setCreateTime(LocalDateTime.parse(createTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            measureManageRequest.setMultipartFiles(files);

            log.info("measureManageRequest:" + measureManageRequest.toString());
            flag = measuremanageService.insertMeasure(measureManageRequest);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            return ResultUtil.databaseError(e.toString());
        }

        return ResultUtil.success(flag);
    }

    @ApiOperation("学生措施管理查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId",value = "学号",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "crop",value = "作物名称",paramType = "query",dataType = "String")
    })
    @GetMapping("/query")
    public Result<Object> queryMeasure(String studentId,String crop){
        List<MeasureManageResponse> measureManageResponseList = null;
        try{
            measureManageResponseList = measuremanageService.queryMeasure(studentId, crop);
        }catch (Exception e){
            return ResultUtil.databaseError(e.toString());
        }
        return ResultUtil.success(measureManageResponseList);
    }
}
