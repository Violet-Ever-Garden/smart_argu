package hzau.sa.backstage.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.CropGrowthPeriodVO;
import hzau.sa.backstage.entity.CropParameterModel;
import hzau.sa.backstage.entity.CropParameterVO;
import hzau.sa.backstage.service.impl.CropParameterServiceImpl;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.LogType;
import hzau.sa.msg.util.ResultUtil;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import hzau.sa.backstage.service.CropParameterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;


/**
 *  控制器
 * @author haokai
 * @date 2020-08-12
 */
@Slf4j
@RestController
@RequestMapping("/cropParameter")
@Api(value = "-API", tags = { "作物参数接口" })
public class CropParameterController extends BaseController {

    @Autowired
    private CropParameterServiceImpl cropParameterService;


    @ApiOperation(value = "分页模糊查参数", notes = "分页模糊查参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "cropId", value = "作物id", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String")
    })
    @GetMapping("/page")
    public Result page(String keyword,String cropId){
        keyword = Convert.toStr(keyword,"");
        Page<CropParameterModel> page = getPage();
        List<CropParameterModel> cropParameterModels = cropParameterService.selectCropParameterListPage(page, Integer.parseInt(cropId), keyword);
        return ResultUtil.success(cropParameterModels);
    }

    @SysLog(prefix = "新增参数", value = LogType.ALL)
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "新增参数", notes = "新增参数")
    @ApiImplicitParam(name = "cropParameterVO", value = "参数实体", paramType = "body", dataType = "CropParameterVO")
    @PostMapping("/add")
    public Result add(@RequestBody CropParameterVO cropParameterVO) {
        boolean save = cropParameterService.save(cropParameterVO);
        if (false == save) {
            return ResultUtil.databaseError();
        } else {
            return ResultUtil.success();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @SysLog(prefix = "删除参数", value = LogType.ALL)
    @ApiOperation(value = "删除参数", notes = "删除参数")
    @ApiImplicitParam(name = "cropParameterId", value = "参数id", paramType = "path", dataType = "String")
    @PostMapping("/delete/{cropParameterId}")
    public Result delete(@PathVariable("cropParameterId") String cropParameterId){
        log.info(String.valueOf(cropParameterId));
        boolean b = cropParameterService.removeById(cropParameterId);
        if(false == b){
            return ResultUtil.databaseError(b);
        }else {
            return ResultUtil.success("成功删除");
        }
    }

    @SysLog(prefix = "批量删除参数", value = LogType.ALL)
    @ApiOperation(value = "批量删除参数", notes = "批量删除参数")
    @ApiImplicitParam(name = "ids[]", value = "参数id数组", paramType = "query", allowMultiple = true,dataType = "String")
    @PostMapping("/deleteList")
    @Transactional(rollbackFor = Exception.class)
    public Result deleteList(@RequestParam(value = "ids[]") String[] ids){
        boolean b = cropParameterService.removeByIds(Arrays.asList(ids));
        if(false==b){
            return ResultUtil.databaseError();
        }else{
            return ResultUtil.success();
        }
    }
    @SysLog(prefix = "更新参数", value = LogType.ALL)
    @ApiOperation(value = "更新参数", notes = "更新参数")
    @ApiImplicitParam(name = "cropParameterVO", value = "作物参数实体类", paramType = "query", dataType = "CropParameterVO")
    @PostMapping("/update")
    public Result update(@RequestBody CropParameterVO cropParameterVO){
        boolean b = cropParameterService.updateById(cropParameterVO);
        if(false == b){
            return ResultUtil.databaseError();
        }else {
            return ResultUtil.success();
        }
    }
}