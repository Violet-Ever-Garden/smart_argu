package hzau.sa.backstage.controller;


import hzau.sa.backstage.entity.SenesorModel;
import hzau.sa.backstage.service.impl.SensorServiceImpl;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.LogType;
import hzau.sa.msg.util.ResultUtil;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.SensorVO;
import hzau.sa.backstage.service.SensorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;

import java.util.Arrays;


/**
 *  控制器
 * @author haokai
 * @date 2020-08-25
 */
@RestController
@RequestMapping("/sensor")
@Api(value = "-API", tags = { "传感器管理接口" })
public class SensorController extends BaseController {

    @Autowired
    private SensorServiceImpl sensorService;

    @SysLog(prefix = "新增传感器", value = LogType.ALL)
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "新增传感器" ,notes = "新增传感器")
    @ApiImplicitParam(name = "sensorVO" , value = "传感器实体类" , paramType = "body" , dataType = "SensorVO")
    @PostMapping("/insert")
    public Result insert(@RequestBody SensorVO sensorVO){
        boolean save = sensorService.save(sensorVO);
        if(false==save){
            return ResultUtil.databaseError("插入失败");
        }
        return ResultUtil.success("插入成功");
    }


    @SysLog(prefix = "更新传感器", value = LogType.ALL)
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "更新传感器" ,notes = "更新传感器")
    @ApiImplicitParam(name = "sensorVO" , value = "传感器实体类" , paramType = "body" , dataType = "SensorVO")
    @PostMapping("/update")
    public Result update(@RequestBody SensorVO sensorVO){
        boolean update = sensorService.updateById(sensorVO);
        if(false==update){
            return ResultUtil.databaseError("更新失败");
        }
        return ResultUtil.success("更新成功");
    }

    @SysLog(prefix = "删除传感器", value = LogType.ALL)
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "删除传感器" ,notes = "删除传感器")
    @ApiImplicitParam(name = "sensorId" , value = "传感器实体类" , paramType = "path" , dataType = "String")
    @PostMapping("/deleteONe/{sensorId}")
    public Result deleteONe(@PathVariable int sensorId){
        boolean removeById = sensorService.removeById(sensorId);
        if(false==removeById){
            return ResultUtil.databaseError("删除失败");
        }
        return ResultUtil.success("删除成功");
    }

    @SysLog(prefix = "批量删除传感器", value = LogType.ALL)
    @ApiOperation(value = "批量删除传感器", notes = "批量删除传感器")
    @ApiImplicitParam(name = "ids[]", value = "传感器id数组", paramType = "query", allowMultiple = true,dataType = "String")
    @PostMapping("/deleteList")
    @Transactional(rollbackFor = Exception.class)
    public Result deleteList(@RequestParam(value = "ids[]") String[] ids){
        boolean b = sensorService.removeByIds(Arrays.asList(ids));
        if(false==b){
            return ResultUtil.databaseError("删除失败");
        }else{
            return ResultUtil.success("删除成功");
        }
    }


    @ApiOperation(value = "分页条件查询", notes = "分页条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sensorName", value = "传感器名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "baseName", value = "基地名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String"),

    })
    @GetMapping("/page")
    public Result page(String sensorName,String baseName){
        Page<SenesorModel> senesorModelPage = getPage();
        return ResultUtil.success(sensorService.selectSensorModel(senesorModelPage,baseName,sensorName));
    }
}