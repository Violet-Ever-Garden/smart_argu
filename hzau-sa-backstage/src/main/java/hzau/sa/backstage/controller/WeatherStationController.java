package hzau.sa.backstage.controller;


import hzau.sa.backstage.entity.WeatherStationModel;
import hzau.sa.backstage.service.impl.WeatherStationServiceImpl;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.LogType;
import hzau.sa.msg.util.ResultUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.WeatherStationVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;


/**
 *  控制器
 * @author haokai
 * @date 2020-08-26
 */
@RestController
@RequestMapping("/weatherStation")
@Api(value = "-API", tags = { "气象站接口" })
public class WeatherStationController extends BaseController {

    @Autowired
    private WeatherStationServiceImpl weatherStationService;

    @SysLog(prefix = "新增气象站", value = LogType.ALL)
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "新增气象站" ,notes = "新增气象站")
    @ApiImplicitParam(name = "weatherStationVO" , value = "气象站实体类" , paramType = "body" , dataType = "WeatherStationVO")
    @PostMapping("/insert")
    public Result insert(@RequestBody WeatherStationVO weatherStationVO){
        boolean save = weatherStationService.save(weatherStationVO);
        if(false==save){
            return ResultUtil.databaseError("插入失败");
        }
        return ResultUtil.success("插入成功");
    }


    @SysLog(prefix = "更新气象站", value = LogType.ALL)
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "更新气象站" ,notes = "更新气象站")
    @ApiImplicitParam(name = "weatherStationVO" , value = "气象站实体类" , paramType = "body" , dataType = "WeatherStationVO")
    @PostMapping("/update")
    public Result update(@RequestBody WeatherStationVO weatherStationVO){
        boolean update = weatherStationService.updateById(weatherStationVO);
        if(false==update){
            return ResultUtil.databaseError("更新失败");
        }
        return ResultUtil.success("更新成功");
    }

    @SysLog(prefix = "删除气象站", value = LogType.ALL)
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "删除气象站" ,notes = "删除气象站")
    @ApiImplicitParam(name = "weatherStationId" , value = "气象站实体类" , paramType = "path" , dataType = "String")
    @PostMapping("/deleteONe/{weatherStationId}")
    public Result deleteONe(@PathVariable int weatherStationId){
        boolean removeById = weatherStationService.removeById(weatherStationId);
        if(false==removeById){
            return ResultUtil.databaseError("删除失败");
        }
        return ResultUtil.success("删除成功");
    }

    @SysLog(prefix = "批量删除气象站", value = LogType.ALL)
    @ApiOperation(value = "批量删除气象站", notes = "批量删除气象站")
    @ApiImplicitParam(name = "ids[]", value = "气象站id数组", paramType = "query", allowMultiple = true,dataType = "String")
    @PostMapping("/deleteList")
    @Transactional(rollbackFor = Exception.class)
    public Result deleteList(@RequestParam(value = "ids[]") String[] ids){
        boolean b = weatherStationService.removeByIds(Arrays.asList(ids));
        if(false==b){
            return ResultUtil.databaseError("删除失败");
        }else{
            return ResultUtil.success("删除成功");
        }
    }


    @ApiOperation(value = "分页条件查询", notes = "分页条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sensorName", value = "气象站名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "baseName", value = "基地名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String"),

    })
    @GetMapping("/page")
    public Result page(String sensorName,String baseName){
        Page<WeatherStationModel> senesorModelPage = getPage();
        return ResultUtil.success(weatherStationService.selectSensorModel(senesorModelPage,baseName,sensorName));
    }

    @ApiOperation(value = "文件导入", notes = "文件导入")
    @PostMapping("/uploadFile")
    public Result uploadFile(@ApiParam MultipartFile file) throws IOException {
        weatherStationService.insertByFile(file);
        return ResultUtil.success();
    }
}