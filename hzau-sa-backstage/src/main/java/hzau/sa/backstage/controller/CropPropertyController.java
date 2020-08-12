package hzau.sa.backstage.controller;

import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;

import hzau.sa.backstage.service.impl.CropPropertyServiceImpl;
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
import hzau.sa.backstage.entity.CropPropertyVO;

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
@RequestMapping("/cropProperty")
@Api(value = "-API", tags = { "相关接口" })
public class CropPropertyController extends BaseController {

    @Autowired
    private CropPropertyServiceImpl cropPropertyService;

    @ApiOperation(value = "新增属性", notes = "新增属性")
    @ApiImplicitParam(name = "cropPropertyVO", value = "属性实体", paramType = "body", dataType = "CropPropertyVO")
    @PostMapping("/add")
    public Result add(@RequestBody CropPropertyVO cropPropertyVO) {
        boolean save = cropPropertyService.save(cropPropertyVO);
        if (false == save) {
            return ResultUtil.databaseError();
        } else {
            return ResultUtil.success();
        }
    }


    @ApiOperation(value = "删除属性", notes = "属性参数")
    @ApiImplicitParam(name = "cropPropertyId", value = "属性id", paramType = "path", dataType = "int")
    @PostMapping("/delete/{cropPropertyId}")
    public Result delete(@PathVariable("cropPropertyId") int cropPropertyId){
        log.info(String.valueOf(cropPropertyId));
        boolean b = cropPropertyService.removeById(cropPropertyId);
        if(false == b){
            return ResultUtil.databaseError(b);
        }else {
            return ResultUtil.success("成功删除");
        }
    }


    @ApiOperation(value = "批量删除属性", notes = "批量删除属性")
    @ApiImplicitParam(name = "ids", value = "属性id数组", paramType = "query", allowMultiple = true,dataType = "Integer")
    @PostMapping("/deleteList")
    @Transactional(rollbackFor = Exception.class)
    public Result deleteList(@RequestParam(value = "ids[]") Integer[] ids){
        boolean b = cropPropertyService.removeByIds(Arrays.asList(ids));
        if(false==b){
            return ResultUtil.databaseError();
        }else{
            return ResultUtil.success();
        }
    }
    //TODO.....
}