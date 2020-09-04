package hzau.sa.sensorData.controller;

import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;

import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import hzau.sa.sensorData.entity.ControlInteractionModel;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
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
import hzau.sa.sensorData.entity.ControlInteractionVO;
import hzau.sa.sensorData.service.ControlInteractionService;

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
@RequestMapping("/controlInteraction")
@Api(value = "-API", tags = { "智能灌溉相关接口" })
public class ControlInteractionController extends BaseController {

    @Autowired
    private ControlInteractionService controlInteractionService;

    /**
     * 按学生id分页列表
     */
    @SysLog(prefix = "按学生id分页列表")
    @ApiOperation("按学生id分页列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "studentId", value = "学生id", required = true,paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
        @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String")
    })
    @GetMapping("/pageByStudentId")
    public Result pageByStudentId(String studentId){
        Page<ControlInteractionModel> page = getPage();
        IPage<ControlInteractionModel> iPage = controlInteractionService.getAllControlInteractionByStudentId(page, studentId);
        return ResultUtil.success(iPage);
    }
    /**
     * 按老师id分页列表
     */
    @SysLog(prefix = "按老师id分页列表")
    @ApiOperation("按老师id分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId", value = "老师id", required = true,paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String")
    })
    @GetMapping("/pageByTeacherId")
    public Result pageByTeacherId(String teacherId){
        Page<ControlInteractionModel> page = getPage();
        IPage<ControlInteractionModel> iPage = controlInteractionService.getAllControlInteractionByTeacherId(page, teacherId);
        return ResultUtil.success(iPage);
    }

    /**
     * 按控制交互id更新远程状态
     * @param controlInteractionId
     * @param remoteStatus
     * @return
     */
    @SysLog(prefix = "按控制交互id更新远程状态")
    @ApiOperation("按控制交互id更新远程状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "controlInteractionId",value = "控制交互id",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "remoteStatus",value = "远程状态",paramType = "query",dataType = "String")
    })
    @PostMapping("/updateControlInteraction")
    public Result updateControlInteraction(String controlInteractionId,String remoteStatus){
        return controlInteractionService.updateControlInteraction(Integer.valueOf(controlInteractionId),remoteStatus);
    }

}