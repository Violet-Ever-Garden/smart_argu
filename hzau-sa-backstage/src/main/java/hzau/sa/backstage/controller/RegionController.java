package hzau.sa.backstage.controller;

import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;

import cn.hutool.core.convert.Convert;
import hzau.sa.backstage.entity.RegionModel;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import hzau.sa.backstage.entity.RegionVO;
import hzau.sa.backstage.service.RegionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;


/**
 *  控制器
 * @author wuyihu
 * @date 2020-08-28
 */
@Slf4j
@RestController
@RequestMapping("/region")
@Api(value = "-API", tags = { "区域接口" })
public class RegionController extends BaseController {

    @Autowired
    private RegionService regionService;

    /**
     * 增加区域
     */
    @SysLog(prefix = "增加区域")
    @ApiOperation("增加区域")
    @ApiImplicitParam(name = "regionModel", value = "实体", paramType = "body", dataType = "RegionModel")
    @PostMapping("/addRegion")
    public Result addRegion(@RequestBody RegionModel regionModel){
        return regionService.addRegion(regionModel);
    }

    /**
     * 更新区域
     */
    @SysLog(prefix = "更新区域")
    @ApiOperation("更新区域")
    @ApiImplicitParam(name = "regionModel", value = "实体", paramType = "body", dataType = "RegionModel")
    @PostMapping("/updateRegion")
    public Result updateRegion(@RequestBody RegionModel regionModel){
        return regionService.updateRegion(regionModel);
    }

    /**
     * 删除区域
     * @param regionId
     * @return
     */
    @SysLog(prefix = "删除区域")
    @ApiOperation("删除区域")
    @ApiImplicitParam(name = "regionId", value = "区域id", paramType = "query", dataType = "String")
    @PostMapping("/deleteRegion")
    public Result deleteRegion(String regionId){
        return regionService.deleteRegion(Integer.valueOf(regionId));
    }

    /**
     * 批量删除区域
     * @param regionIds
     * @return
     */
    @SysLog(prefix = "批量删除区域")
    @ApiOperation("批量删除区域")
    @ApiImplicitParam(name = "regionIds", value = "区域ids", paramType = "query",allowMultiple = true, dataType = "Integer")
    @PostMapping("/deleteRegions")
    public Result deleteRegions(Integer[] regionIds){
        return regionService.deleteRegions(regionIds);
    }

    @SysLog(prefix = "按名字分页模糊查询")
    @ApiOperation(value = "按名字分页模糊查询", notes = "按名字分页模糊查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "regionName", value = "区域关键字", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String"),

    })
    @GetMapping("/page")
    public Result page(String regionName){
        regionName= Convert.toStr(regionName,"");
        Page<RegionModel> page = getPage();
        IPage<RegionModel> iPage = regionService.page(page, regionName);
        return ResultUtil.success(iPage);
    }
}