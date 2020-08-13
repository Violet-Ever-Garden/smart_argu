package hzau.sa.backstage.controller;

import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;

import hzau.sa.backstage.service.impl.CropGrowthPeriodServiceImpl;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import hzau.sa.backstage.entity.CropGrowthPeriodVO;
import hzau.sa.backstage.service.CropGrowthPeriodService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;


/**
 *  控制器
 * @author haokai
 * @date 2020-08-13
 */
@Slf4j
@RestController
@RequestMapping("/cropGrowthPeriod")
@Api(value = "-API", tags = { "作物生育期接口" })
public class CropGrowthPeriodController extends BaseController {

    @Autowired
    private CropGrowthPeriodServiceImpl cropGrowthPeriodService;

    @ApiOperation(value = "新增生育期", notes = "新增生育期")
    @ApiImplicitParam(name = "CropGrowthPeriodVO", value = "作物生育期实体", paramType = "body", dataType = "CropGrowthPeriodVO")
    @PostMapping("/add")
    public Result add(@RequestBody CropGrowthPeriodVO CropGrowthPeriodVO) {
        boolean save = cropGrowthPeriodService.save(CropGrowthPeriodVO);
        if (false == save) {
            return ResultUtil.databaseError();
        } else {
            return ResultUtil.success();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "删除生育期", notes = "删除生育期")
    @ApiImplicitParam(name = "cropGrowthPeriodId", value = "生育期id", paramType = "path", dataType = "String")
    @PostMapping("/delete/{cropGrowthPeriodId}")
    public Result delete(@PathVariable("cropGrowthPeriodId") String cropGrowthPeriodId){
        log.info(String.valueOf(cropGrowthPeriodId));
        boolean b = cropGrowthPeriodService.removeById(cropGrowthPeriodId);
        if(false == b){
            return ResultUtil.databaseError(b);
        }else {
            return ResultUtil.success("成功删除");
        }
    }


    @ApiOperation(value = "批量删除生育期", notes = "批量删除生育期")
    @ApiImplicitParam(name = "ids", value = "生育期id数组", paramType = "query", allowMultiple = true,dataType = "String")
    @PostMapping("/deleteList")
    @Transactional(rollbackFor = Exception.class)
    public Result deleteList(@RequestParam(value = "ids[]") String[] ids){
        boolean b = cropGrowthPeriodService.removeByIds(Arrays.asList(ids));
        if(false==b){
            return ResultUtil.databaseError();
        }else{
            return ResultUtil.success();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "获取作物生育期", notes = "获取作物生育期")
    @ApiImplicitParam(name = "cropId", value = "作物id", paramType = "path", dataType = "String")
    @GetMapping("/list/{cropId}")
    public Result list(@PathVariable("cropId")String cropId){
        QueryWrapper<CropGrowthPeriodVO> queryWrapper = new QueryWrapper();
        queryWrapper.eq("cropId",cropId);
        return ResultUtil.success(cropGrowthPeriodService.list(queryWrapper));
    }

    //TODO.....
}