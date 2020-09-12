package hzau.sa.sensorData.controller;

import cn.hutool.core.convert.Convert;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import hzau.sa.msg.util.ZipUtil;
import hzau.sa.sensorData.service.SensorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@RequestMapping("/sensorData")
@RestController
@Slf4j
@Api(value = "传感器数据-API",tags = "传感器数据相关接口")
public class SensorDataController {
    @Autowired
    SensorService sensorService;

    @ApiOperation(value = "根据用户id查区域", notes = "根据用户id查区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = "角色", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "id",value = "用户id",paramType = "query",dataType = "String")
    })
    @GetMapping("/getRegionById")
    public Result getRegionById(String role,String id){
        return ResultUtil.success(sensorService.getRegionById(role,id));
    }


    @ApiOperation(value = "获取数据列表", notes = "获取数据")
    @GetMapping("/getAll")
    public Result getAll(){
        return  ResultUtil.success(sensorService.getCurAll());
    }

    @ApiOperation(value = "获取某网关当前传感器数据", notes = "获取某网关当前传感器数据")
    @ApiImplicitParam(name = "gatewayAddress", value = "网关地址", paramType = "path", dataType = "String")
    @GetMapping("/getOneNowGatewayDate/{gatewayAddress}")
    public Result getOneNowGatewayDate(@PathVariable("gatewayAddress") String gatewayAddress){
        return ResultUtil.success(sensorService.getNowSensorData(gatewayAddress));
    }


    @ApiOperation(value = "根据区域查网关", notes = "获取某网关当前传感器数据")
    @ApiImplicitParam(name = "regionName", value = "区域名称", paramType = "path", dataType = "String")
    @GetMapping("/getGatewayByRegion/{regionName}")
    public Result getGatewayByRegion(@PathVariable("regionName") String regionName){
        return ResultUtil.success(sensorService.getGatewayByRegion(regionName));
    }
    @ApiOperation(value = "查询某网关历史数据", notes = "查询某网关历史数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gatewayAddress", value = "网关地址", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime",value = "起始时间(yyyy-mm-dd hh:MM:ss)",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "endTime",value = "终止时间(yyyy-mm-dd hh:MM:ss)",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",required = true,dataType = "String")

    })
    @GetMapping("/getOneGatewayHistoryData")
    public Result getOneGatewayHistoryData(String gatewayAddress,String startTime ,String endTime,int page,int limit ){
        page = Convert.toInt(page,1);
        limit = Convert.toInt(limit,20);
        return  ResultUtil.success(sensorService.getOneGatewayHistoryDataPage(gatewayAddress,startTime,endTime,page,limit));
    }


    @ApiOperation(value = "查询某网关某传感器历史数据", notes = "查询某网关某传感器历史数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gatewayAddress", value = "网关地址", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime",value = "起始时间(yyyy-mm-dd hh:MM:ss)",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "endTime",value = "终止时间(yyyy-mm-dd hh:MM:ss)",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "sensorName",value = "传感器名称",paramType = "query",dataType = "String")
    })
    @GetMapping("/getOneSensorHistoryData")
    public Result getOneSensorHistoryData(String gatewayAddress,String startTime ,String endTime ,String sensorName){
        return  ResultUtil.success(sensorService.getOneSensorHistoryData(gatewayAddress,startTime,endTime,sensorName));
    }


    @ApiOperation(value = "获取首页传感器数据", notes = "获取首页传感器数据")
    @GetMapping("/indexSensorData")
    public Result indexSensorData(){
        return getOneNowGatewayDate("1200201909171086");
    }


    @ApiOperation(value = "按小时获取网关历史数据" ,notes = "按小时获取网关历史数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gatewayAddress", value = "网关地址", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "hours",value = "小时计算",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",required = true,dataType = "String")


    })
    @GetMapping("/getGatewayDataByHours")
    public Result  getGatewayDataByHours(String gatewayAddress,Long hours,int page,int limit){
        page = Convert.toInt(page,1);
        limit = Convert.toInt(limit,20);
        return ResultUtil.success(sensorService.getGatewayDataByHoursPage(gatewayAddress,hours,page,limit));
    }

    @ApiOperation(value = "按小时获取传感器历史数据" ,notes = "按小时获取传感器历史数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gatewayAddress", value = "网关地址", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "hours",value = "小时计算",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "sensorName",value = "传感器名称",paramType = "query",dataType = "String")
    })
    @GetMapping("/getSensorDataByHours")
    public Result  getSensorDataByHours(String gatewayAddress,Long hours,String sensorName){
        return ResultUtil.success(sensorService.getOneSensorDataByHours(gatewayAddress,sensorName,hours));
    }


    @ApiOperation(value = "导出某网关历史数据", notes = "导出某网关历史数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gatewayAddress", value = "网关地址", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "startTime",value = "起始时间(yyyy-mm-dd hh:MM:ss)",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "endTime",value = "终止时间(yyyy-mm-dd hh:MM:ss)",paramType = "query",dataType = "String")
    })
    @GetMapping("/exportSensorData")
    public void exportSensorData(String gatewayAddress, String startTime, String endTime, HttpServletResponse httpServletResponse){
        try{
            sensorService.exportGatewayData(gatewayAddress,startTime,endTime,httpServletResponse);
        }catch (Exception e){
            log.error(e.toString());
        }
    }


    @ApiOperation(value = "按小时导出网关历史数据" ,notes = "按小时导出网关历史数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gatewayAddress", value = "网关地址", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "hours",value = "小时计算",paramType = "query",dataType = "String")
    })
    @GetMapping("/exportGatewayDataByHours")
    public void  exportGatewayDataByHours(String gatewayAddress,Long hours,HttpServletResponse httpServletResponse){
        sensorService.exportGatewayDataByHours(gatewayAddress,hours,httpServletResponse);
    }


}
