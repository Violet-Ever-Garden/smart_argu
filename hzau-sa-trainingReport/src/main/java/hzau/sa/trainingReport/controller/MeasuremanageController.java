package hzau.sa.trainingReport.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.LogType;
import hzau.sa.msg.util.ResultUtil;
import hzau.sa.trainingReport.entity.*;
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
import java.util.*;

/**
 * @author LvHao
 * @Description :
 * @date 2020-08-18 10:42
 */
@Slf4j
@RestController
@RequestMapping("/measuremanage")
@Api(value = "措施管理-API接口",tags = "措施管理相关接口")
public class MeasuremanageController extends BaseController {

    @Resource
    private MeasuremanageService measuremanageService;

    @SysLog(prefix = "新增学生措施信息",value = LogType.ALL)
    @ApiOperation("新增学生措施信息")
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
            @ApiImplicitParam(name = "studentId",value = "学号",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "crop",value = "作物名称",paramType = "query",required = true,dataType = "String")
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

    @SysLog(prefix = "修改学生措施信息",value = LogType.ALL)
    @ApiOperation("修改学生措施信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "measureManageId",value = "措施ID",required = true,paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "measure",value = "措施名称",required = true,paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "measureContent",value = "措施内容",required = true,paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "createTime",value = "创建时间",required = true,paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "ids",value = "需要删除图片ID数组",required = true,paramType = "form",allowMultiple = true,dataType = "String")
    })
    @PutMapping("/update")
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> updateMeasure(String measureManageId,
                                        String measure,
                                        String measureContent,
                                        String createTime,
                                        @RequestParam("ids") String[] ids,
                                        @ApiParam(name = "files",value = "图片数组",required = true) MultipartFile[] files){
        Object savePoint = null;
        boolean flag = false;
        try{
            savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();

            MeasureManageRequest measureManageRequest = new MeasureManageRequest();
            measureManageRequest.setMeasure(measure);
            measureManageRequest.setMeasureContent(measureContent);
            measureManageRequest.setCreateTime(LocalDateTime.parse(createTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            measureManageRequest.setMultipartFiles(files);

            log.info("measureManageRequest:" + measureManageRequest.toString());
            flag = measuremanageService.updateMeasure(measureManageRequest, Integer.valueOf(measureManageId),ids);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            return ResultUtil.databaseError(e.toString());
        }

        return ResultUtil.success(flag);
    }

    @SysLog(prefix = "删除学生措施信息",value = LogType.ALL)
    @ApiOperation("删除学生措施信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "measureManageId",value = "需要删除措施ID",required = true,paramType = "path",dataType = "String")
    })
    @DeleteMapping("/delete/{measureManageId}")
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> deleteMeasure(@PathVariable("measureManageId") String measureManageId){
        Object savePoint = null;
        boolean flag = false;
        try{
            savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();

            log.info("measureManageId:" + measureManageId);
            flag = measuremanageService.deleteMeasure(Integer.valueOf(measureManageId));
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            return ResultUtil.databaseError(e.toString());
        }
        return ResultUtil.success(flag);
    }

    @ApiOperation("老师管理班级查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "grade",value = "年级",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "name",value = "名称",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "teacherId",value = "老师工号",required = true,paramType = "query",dataType = "String")
    })
    @GetMapping("/queryClassOfTeacher")
    public Result<Object> queryClassOfTeacher(String teacherId,String grade,String name){
        Page page = getPage();

        QueryWrapper<AsTeacherclassVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AsTeacherclassVO::getTeacherId,teacherId);
        if(StrUtil.isNotBlank(name)){
            List<Integer> classIds = measuremanageService.queryClassIdByName(name);
            if(classIds.isEmpty()){
                return ResultUtil.error("班级不存在");
            }
            queryWrapper.lambda().in(AsTeacherclassVO::getClassId,classIds);
        }
        if(StrUtil.isNotBlank(grade)){
            queryWrapper.lambda().eq(AsTeacherclassVO::getGradeId,measuremanageService.queryGradeIdByName(grade));
        }

        return ResultUtil.success(measuremanageService.queryClassByTeacherId(page,queryWrapper));
    }

    @ApiOperation("班级下所有学生查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "studentName",value = "学生姓名",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "classId",value = "班级ID",required = true,paramType = "query",dataType = "String")
    })
    @GetMapping("/queryStudentOfClass")
    public Result<Object> queryStudentByClassId(String classId,String studentName){
        Page page = getPage();

        QueryWrapper<StudentVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(StudentVO::getClassId,classId);
        if(StrUtil.isNotBlank(studentName)){
            List<String> studentIds = measuremanageService.queryStudentIdByName(studentName);
            if(studentIds.isEmpty()){
                return ResultUtil.error("学生不存在");
            }
            queryWrapper.lambda().in(StudentVO::getStudentId,studentIds);
        }

        return ResultUtil.success(measuremanageService.queryStudentByClassId(page,queryWrapper));
    }
}
