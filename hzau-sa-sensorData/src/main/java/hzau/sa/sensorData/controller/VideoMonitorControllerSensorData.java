package hzau.sa.sensorData.controller;

import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import hzau.sa.sensorData.entity.VideoMonitorModel;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.sensorData.service.VideoMonitorServiceSensorData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;


/**
 *  控制器
 * @author lvhao
 * @date 2020-09-04
 */
@Slf4j
@RestController
@RequestMapping("/videoMonitor")
@Api(value = "-API", tags = { "田间监测相关接口" })
public class VideoMonitorControllerSensorData extends BaseController {

    @Autowired
    private VideoMonitorServiceSensorData videoMonitorServiceSensorData;

    /**
     * 按学生id分页列表
     */
    @SysLog(prefix = "按学生id分页列表")
    @ApiOperation("按学生id分页列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "studentId", value = "学生id", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
        @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String")
    })
    @GetMapping("/pageByStudentId")
    public Result pageByStudentId(String studentId){
        Page<VideoMonitorModel> page = getPage();
        IPage<VideoMonitorModel> iPage = videoMonitorServiceSensorData.pageByStudentId(page, studentId);
        return ResultUtil.success(iPage);
    }

    /**
     * 按老师id分页列表
     */
    @SysLog(prefix = "按老师id分页列表")
    @ApiOperation("按老师id分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId", value = "老师id", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String")
    })
    @GetMapping("/pageByTeacherId")
    public Result pageByTeacherId(String teacherId){
        Page<VideoMonitorModel> page = getPage();
        IPage<VideoMonitorModel> iPage = videoMonitorServiceSensorData.pageByTeacherId(page, teacherId);
        return ResultUtil.success(iPage);
    }
}