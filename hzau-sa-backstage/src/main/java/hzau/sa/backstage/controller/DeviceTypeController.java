package hzau.sa.backstage.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.DeviceTypeIdAndName;
import hzau.sa.backstage.entity.DeviceTypeModel;
import hzau.sa.backstage.entity.DeviceTypeVO;
import hzau.sa.backstage.entity.EarlyWarningModel;
import hzau.sa.backstage.service.impl.DeviceTypeServiceImpl;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.LogType;
import hzau.sa.msg.util.ResultUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;

import java.util.Arrays;
import java.util.List;


/**
 *  控制器
 * @author haokai
 * @date 2020-08-21
 */
@RestController
@RequestMapping("/deviceType")
@Api(value = "-API", tags = { "设备管理" })
public class DeviceTypeController extends BaseController {

    @Autowired
    private DeviceTypeServiceImpl deviceTypeService;


    @SysLog(prefix = "新增设备类型", value = LogType.ALL)
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "新增设备类型" ,notes = "新增设备类型")
    @ApiImplicitParam(name = "deviceTypeModel",value = "设备类型模板",paramType = "body",dataType = "DeviceTypeModel")
    @PostMapping("/insert")
    public Result insert(@RequestBody DeviceTypeModel deviceTypeModel){
        boolean save = deviceTypeService.insertDeviceType(deviceTypeModel);
        if(false==save){
            return ResultUtil.databaseError("新增失败");
        }
        return ResultUtil.success("新增成功");
    }

    @SysLog(prefix = "更新设备类型", value = LogType.ALL)
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "更新设备类型" ,notes = "更新设备类型")
    @ApiImplicitParam(name = "deviceTypeModel",value = "设备类型模板",paramType = "body",dataType = "DeviceTypeModel")
    @PostMapping("/update")
    public Result update(@RequestBody DeviceTypeModel deviceTypeModel){
        boolean save = deviceTypeService.update(deviceTypeModel);
        if(false==save){
            return ResultUtil.databaseError("更新失败");
        }
        return ResultUtil.success("更新成功");
    }

    @SysLog(prefix = "删除设备类型", value = LogType.ALL)
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "删除设备类型" ,notes = "删除设备类型")
    @ApiImplicitParam(name = "deviceTypeId",value = "设备类型id",paramType = "path",dataType = "String")
    @PostMapping("/deleteOne/{deviceTypeId}")
    public Result deleteOne(@PathVariable int deviceTypeId){
        boolean removeById = deviceTypeService.removeById(deviceTypeId);
        if(false==removeById){
            return ResultUtil.databaseError("删除失败");
        }
        return ResultUtil.success("删除成功");
    }




    @SysLog(prefix = "批量删除设备类型", value = LogType.ALL)
    @ApiOperation(value = "批量删除设备类型", notes = "批量删除设备类型")
    @ApiImplicitParam(name = "ids[]", value = "设备类型id数组", paramType = "query", allowMultiple = true,dataType = "String")
    @PostMapping("/deleteList")
    @Transactional(rollbackFor = Exception.class)
    public Result deleteList(@RequestParam(value = "ids[]") String[] ids){
        boolean b = deviceTypeService.removeByIds(Arrays.asList(ids));
        if(false==b){
            return ResultUtil.databaseError();
        }else{
            return ResultUtil.success();
        }
    }


    @ApiOperation("设备分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String"),
    })
    @GetMapping("/page")
    public Result page(){
        Page<DeviceTypeVO> page = getPage();
        IPage<DeviceTypeVO> deviceTypeVOIPage = deviceTypeService.page(page);
        return ResultUtil.success(deviceTypeVOIPage);
    }

    @ApiOperation("设备id,name查询")
    @GetMapping("/getIdAndName")
    public Result getIdAndName(){
        List<DeviceTypeIdAndName> deviceTypeIdAndNames = deviceTypeService.selectDeviceTypeModel();
        return ResultUtil.success(deviceTypeIdAndNames);
    }

    @ApiOperation("通过设备id，查询预警方案")
    @ApiImplicitParam(name = "deviceTypeId",value = "设备类型id",paramType = "path",dataType = "String")
    @GetMapping("/getEarlyWarningByDeviceTypeId/{deviceTypeId}")
    public Result getEarlyWarningByDeviceTypeId(@PathVariable int deviceTypeId){
        return ResultUtil.success(deviceTypeService.selectEarlyWarningByDeviceTypeId(deviceTypeId));
    }
}