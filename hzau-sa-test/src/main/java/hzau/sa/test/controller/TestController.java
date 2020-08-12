package hzau.sa.test.controller;

import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.entity.LogVO;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.LogType;
import hzau.sa.msg.util.ResultUtil;
import hzau.sa.test.entity.Test;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author LvHao
 * @Description :
 * @date 2020-08-09 11:30
 */
@RestController
@Api(value = "Test-API",tags = "测试")
public class TestController {

    @ApiOperation(value = "测试",notes = "test")
    @SysLog(prefix = "asdas",value = LogType.ALL)
    @GetMapping("/test")
    @ResponseBody
    @ApiImplicitParam(name = "test",value = "测试", dataType = "String")
    public Result<Object> test(String test){
        return ResultUtil.success(test);
    }

    @SysLog(prefix = "test",value = LogType.ALL)
    @ApiOperation(value = "POST测试",notes = "post")
    @ApiImplicitParam(name = "logVO",value = "POST测试",dataType = "LogVO")
    @ResponseBody
    @PostMapping("/post")
    public Result<Object> postTest(@RequestBody LogVO logVO){
        try{
            return ResultUtil.success(logVO);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.error();
        }
    }
}
