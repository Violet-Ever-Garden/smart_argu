package hzau.sa.backstage.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.CropModel;
import hzau.sa.backstage.entity.CropVO;
import hzau.sa.backstage.service.impl.CropServiceImpl;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.LogType;
import hzau.sa.msg.util.ResultUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RequestMapping("/crop")
@RestController
@Api(value = "-API", tags = { "作物接口" })
public class CropController extends BaseController {
    @Autowired
    CropServiceImpl cropService;


    @SysLog(prefix = "新增作物", value = LogType.ALL)
    @ApiOperation(value = "新增作物", notes = "新增作物")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cropName", value = "作物名", required = true, paramType = "form", dataType = "String"),
            //@ApiImplicitParam(name = "picture", value = "图片" , paramType = "form", dataType = "MultipartFile")
           })
    @PostMapping("/save")
    public Result save(String cropName ,@ApiParam MultipartFile picture){
        return cropService.insert(cropName,picture);
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
    public Result deleteList(@RequestParam(value = "ids[]") Integer[] ids){
        boolean b = cropService.removeByIdsAndPicture(Arrays.asList(ids));
        if(false==b){
            return ResultUtil.databaseError();
        }else{
        return ResultUtil.success();
        }
    }


    @ApiOperation(value = "无图片条件分页查询", notes = "条件分页查询")
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

    @ApiOperation(value = "有图片无分页查询", notes = "有图片无分页查询")
    @GetMapping("/listWithUrl")
    public Result listWithUrl(){
        List<CropModel> cropModels = cropService.listWithUrl();
        return ResultUtil.success(cropModels);
    }



    @SysLog(prefix = "更新作物", value = LogType.ALL)
    @ApiOperation(value = "更新作物", notes = "更新作物")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cropId", value = "作物id", required = true, paramType = "form", dataType = "String"),
            @ApiImplicitParam(name = "cropName", value = "作物名", required = true, paramType = "form", dataType = "String"),
            //@ApiImplicitParam(name = "picture", value = "图片" , paramType = "form", dataType = "MultipartFile")
    })
    @PostMapping("/update")
    public Result update(int cropId,String cropName ,@ApiParam MultipartFile picture){
        return cropService.updateCrop(cropId,cropName,picture);
    }
}
