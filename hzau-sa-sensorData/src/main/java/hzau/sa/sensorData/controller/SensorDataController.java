package hzau.sa.sensorData.controller;

import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import hzau.sa.sensorData.service.SensorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/sensorData")
@RestController
@Slf4j
@Api(value = "传感器数据-API",tags = "传感器数据相关接口")
public class SensorDataController {
    @Autowired
    SensorService sensorService;

    @ApiOperation(value = "获取数据列表", notes = "获取数据")
    @GetMapping("/getAll")
    public Result getAll(){
        return  ResultUtil.success(sensorService.getCurAll());
    }
}
