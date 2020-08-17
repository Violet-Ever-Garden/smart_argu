package hzau.sa.backstage.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.CropDTO;
import hzau.sa.backstage.entity.CropVO;
import hzau.sa.backstage.service.impl.CropServiceImpl;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.LogType;
import hzau.sa.msg.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

import static cn.hutool.core.io.file.FileMode.r;

@Slf4j
@RequestMapping("/crop")
@RestController
@Api(value = "-API", tags = { "作物接口" })
public class CropController extends BaseController {
    @Autowired
    CropServiceImpl cropService;


    @SysLog(prefix = "新增作物", value = LogType.ALL)
    @ApiOperation(value = "新增作物", notes = "新增作物")
    @ApiImplicitParam(name = "cropDTO", value = "作物实体", paramType = "body", dataType = "CropDTO")
    @PostMapping("/save")
    public Result save(@RequestBody CropDTO cropDTO){
        return cropService.insert(cropDTO);
    }


    @SysLog(prefix = "删除作物", value = LogType.ALL)
    @ApiOperation(value = "删除作物", notes = "删除作物")
    @ApiImplicitParam(name = "cropId", value = "作物id", paramType = "path", dataType = "String")
    @PostMapping("/delete/{cropId}")
    public Result delete(@PathVariable("cropId")String cropId){
       return cropService.delete(Integer.parseInt(cropId));
    }


    @SysLog(prefix = "批量删除作物", value = LogType.ALL)
    @ApiOperation(value = "批量删除作物", notes = "批量删除作物")
    @ApiImplicitParam(name = "ids[]", value = "作物id数组", paramType = "query", allowMultiple = true, dataType = "String")
    @PostMapping("/deleteList")
    public Result deleteList(@RequestParam(value = "ids[]") String[] ids){
        boolean b = cropService.removeByIds(Arrays.asList(ids));
        if(false==b){
            return ResultUtil.databaseError();
        }else{
        return ResultUtil.success();
        }
    }


    @ApiOperation(value = "条件分页查询", notes = "条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String"),

    })
    @GetMapping("/page")
    public Result page(String keyword){
        keyword = Convert.toStr(keyword,"");
        Page<CropVO> page = getPage();
        log.info(keyword);
        log.info(page.toString());
        QueryWrapper<CropVO> queryWrapper = new QueryWrapper<CropVO>();
        queryWrapper.lambda().like(CropVO::getCropName,keyword)
                .orderByDesc(CropVO::getCreateTime);
        return ResultUtil.success(cropService.page(page,queryWrapper));
    }

    @SysLog(prefix = "更新作物", value = LogType.ALL)
    @ApiOperation(value = "更新作物", notes = "更新作物")
    @ApiImplicitParam(name = "cropVO", value = "关键字", paramType = "query", dataType = "CropVO")
    @PostMapping("/update")
    public Result update(@RequestBody CropVO cropVO){
        boolean b = cropService.updateById(cropVO);
        if(false == b){
            return ResultUtil.databaseError();
        }else {
            return ResultUtil.success();
        }
    }
}
