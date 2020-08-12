package hzau.sa.backstage.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.validation.Valid;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.CropParameterModel;
import hzau.sa.backstage.entity.CropParameterVO;
import hzau.sa.backstage.service.impl.CropParameterServiceImpl;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.LogType;
import hzau.sa.msg.util.ResultUtil;
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
@RestController
@RequestMapping("/cropParameter")
@Api(value = "-API", tags = { "作物参数接口" })
public class CropParameterController extends BaseController {

    @Autowired
    private CropParameterServiceImpl cropParameterService;


    @ApiOperation(value = "分页模糊查参数", notes = "分页模糊查参数")
    @ApiImplicitParam(name = "param", value = "keyword,cropId", paramType = "query", dataType = "HashMap<String,Object>")
    @GetMapping("/page")
    public Result page(@RequestBody HashMap<String,Object> param){
        String keyword = Convert.toStr(param.get("keyword"),"");
        int cropId = Convert.toInt(param.get("cropId"));
        Page<CropParameterModel> page = getPage();
        List<CropParameterModel> cropParameterModels = cropParameterService.selectCropParameterListPage(page, cropId, keyword);
        return ResultUtil.success(cropParameterModels);
    }

    @SysLog(prefix = "新增参数", value = LogType.ALL)
    @ApiOperation(value = "新增参数", notes = "新增参数")
    @ApiImplicitParam(name = "cropParameterVO", value = "参数实体", paramType = "query", dataType = "CropParameterVO")
    @PostMapping("/add")
    public Result add(@RequestBody CropParameterVO cropParameterVO) {
        boolean save = cropParameterService.save(cropParameterVO);
        if (false == save) {
            return ResultUtil.databaseError();
        } else {
            return ResultUtil.success();
        }
    }

    @SysLog(prefix = "删除参数", value = LogType.ALL)
    @ApiOperation(value = "删除参数", notes = "删除参数")
    @ApiImplicitParam(name = "cropParamterId", value = "参数id", paramType = "path", dataType = "int")
    @PostMapping("/delete")
    public Result delete(int cropParamterId){
        boolean b = cropParameterService.removeById(cropParamterId);
        if(false == b){
            return ResultUtil.databaseError(b);
        }else {
            return ResultUtil.success("成功删除");
        }
    }

    @SysLog(prefix = "批量删除参数", value = LogType.ALL)
    @ApiOperation(value = "批量删除参数", notes = "批量删除参数")
    @ApiImplicitParam(name = "ids", value = "参数id数组", paramType = "query", dataType = "int")
    @PostMapping("/deleteList")
    @Transactional(rollbackFor = Exception.class)
    public Result deleteList(@RequestParam(value = "ids[]") int[] ids){
        boolean b = cropParameterService.removeByIds(Arrays.asList(ids));
        if(false==b){
            return ResultUtil.databaseError();
        }else{
            return ResultUtil.success();
        }
    }
}