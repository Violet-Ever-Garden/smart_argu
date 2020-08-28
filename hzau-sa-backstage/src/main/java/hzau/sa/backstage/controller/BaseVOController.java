package hzau.sa.backstage.controller;

import cn.hutool.core.convert.Convert;
import hzau.sa.backstage.entity.BaseModel;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.Base;
import hzau.sa.backstage.service.BaseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.web.multipart.MultipartFile;


/**
 *  控制器
 * @author lvhao
 * @date 2020-08-26
 */
@Slf4j
@RestController
@RequestMapping("/base")
@Api(value = "-API", tags = { "基地相关接口" })
public class BaseVOController extends BaseController {

    @Autowired
    private BaseService baseService;

    @SysLog(prefix = "增加基地")
    @ApiOperation("增加基地")
    @ApiImplicitParam(name = "baseModel", value = "基地", paramType = "body", dataType = "BaseModel")
    @PostMapping("/addBase")
    public Result addBase(@RequestBody BaseModel baseModel){
        return baseService.addBase(baseModel);
    }

    @SysLog(prefix = "删除基地")
    @ApiOperation("删除基地")
    @ApiImplicitParam(name = "baseId", value = "基地id", paramType = "query", dataType = "String")
    @PostMapping("/deleteBase")
    public Result deleteBase(String baseId){
        return baseService.deleteBase(Integer.valueOf(baseId));
    }

    @SysLog(prefix = "批量删除基地")
    @ApiOperation("批量删除基地")
    @ApiImplicitParam(name = "baseIds", value = "基地ids", paramType = "query",allowMultiple = true,dataType = "Integer")
    @PostMapping("/deleteBases")
    public Result deleteBases(Integer[] baseIds){
        return baseService.deleteBases(baseIds);
    }

    @SysLog(prefix = "更新基地")
    @ApiOperation("更新基地")
    @ApiImplicitParam(name = "baseModel", value = "基地", paramType = "body", dataType = "BaseModel")
    @PostMapping("/updateBase")
    public Result updateBase(@RequestBody BaseModel baseModel){
        return baseService.updateBase(baseModel);
    }

    @SysLog(prefix = "分页")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "baseName",value = "基地名字",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String")
    })
    @GetMapping("/page")
    public Result page(String baseName){
        baseName= Convert.toStr(baseName,"");
        Page<BaseModel> page = getPage();
        IPage<BaseModel> iPage = baseService.page(page, baseName);
        return ResultUtil.success(iPage);
    }

    @SysLog(prefix = "基地模板下载")
    @ApiOperation("基地模板下载")
    @PostMapping("/templateDownload")
    public Result templateDownload(){
        return baseService.templateDownload();
    }

    @SysLog(prefix = "从模板增加基地")
    @ApiOperation(value = "从模板增加基地",notes = "从模板增加基地")
    @PostMapping("/addBaseByTemplate")
    public Result addBaseByTemplate(@RequestParam(value = "file",required = true) MultipartFile multipartFile){
        return baseService.addBaseByTemplate(multipartFile,baseService);
    }
}