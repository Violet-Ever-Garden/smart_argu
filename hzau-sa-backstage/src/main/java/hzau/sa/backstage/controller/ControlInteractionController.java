package hzau.sa.backstage.controller;


import hzau.sa.backstage.entity.ControlInteractionModel;
import hzau.sa.backstage.entity.SensorModel;
import hzau.sa.backstage.entity.SensorVO;
import hzau.sa.backstage.service.impl.ControlInteractionServiceImpl;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.LogType;
import hzau.sa.msg.util.ResultUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.ControlInteractionVO;
import hzau.sa.backstage.service.ControlInteractionService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


/**
 *  控制器
 * @author haokai
 * @date 2020-08-28
 */
@RestController
@RequestMapping("/controlInteraction")
@Api(value = "-API", tags = { "控制交互接口" })
public class ControlInteractionController extends BaseController {

    @Autowired
    private ControlInteractionServiceImpl controlInteractionService;

    @SysLog(prefix = "新增控制交互", value = LogType.ALL)
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "新增控制交互" ,notes = "新增控制交互")
    @ApiImplicitParam(name = "controlInteractionVO" , value = "控制交互实体类" , paramType = "body" , dataType = "ControlInteractionVO")
    @PostMapping("/insert")
    public Result insert(@RequestBody ControlInteractionVO controlInteractionVO){
        boolean save = controlInteractionService.save(controlInteractionVO);
        if(false==save){
            return ResultUtil.databaseError("插入失败");
        }
        return ResultUtil.success("插入成功");
    }

    @SysLog(prefix = "更新控制交互", value = LogType.ALL)
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "更新控制交互" ,notes = "更新控制交互")
    @ApiImplicitParam(name = "controlInteractionVO" , value = "控制交互实体类" , paramType = "body" , dataType = "ControlInteractionVO")
    @PostMapping("/update")
    public Result update(@RequestBody ControlInteractionVO controlInteractionVO){
        boolean update = controlInteractionService.updateById(controlInteractionVO);
        if(false==update){
            return ResultUtil.databaseError("更新失败");
        }
        return ResultUtil.success("更新成功");
    }

    @SysLog(prefix = "删除控制交互", value = LogType.ALL)
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "删除控制交互" ,notes = "删除控制交互")
    @ApiImplicitParam(name = "controlInteractionId" , value = "控制交互实体类" , paramType = "path" , dataType = "String")
    @PostMapping("/deleteONe/{controlInteractionId}")
    public Result deleteONe(@PathVariable int controlInteractionId){
        boolean removeById = controlInteractionService.removeById(controlInteractionId);
        if(false==removeById){
            return ResultUtil.databaseError("删除失败");
        }
        return ResultUtil.success("删除成功");
    }


    @SysLog(prefix = "批量删除控制交互", value = LogType.ALL)
    @ApiOperation(value = "批量删除控制交互", notes = "批量删除控制交互")
    @ApiImplicitParam(name = "ids", value = "控制交互id数组", paramType = "query", allowMultiple = true,dataType = "String")
    @PostMapping("/deleteList")
    @Transactional(rollbackFor = Exception.class)
    public Result deleteList(@RequestParam(value = "ids") ArrayList<Integer> ids){
        boolean b = controlInteractionService.removeByIds(ids);
        if(false==b){
            return ResultUtil.databaseError("删除失败");
        }else{
            return ResultUtil.success("删除成功");
        }
    }

    @ApiOperation(value = "分页条件查询", notes = "分页条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "remoteControlDeviceName", value = "控制交互名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "baseName", value = "基地名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String"),

    })
    @GetMapping("/page")
    public Result page(String remoteControlDeviceName,String baseName){
        Page<ControlInteractionModel> page = getPage();
        return ResultUtil.success(controlInteractionService.selectControlInteractionModel(page,baseName,remoteControlDeviceName));
    }


    @ApiOperation(value = "文件导入", notes = "文件导入")
    @PostMapping("/uploadFile")
    public Result uploadFile(@ApiParam MultipartFile file) throws IOException {
        controlInteractionService.insertByFile(file);
        return ResultUtil.success();
    }
}