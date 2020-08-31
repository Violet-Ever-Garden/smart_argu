package hzau.sa.backstage.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.EarlyWarningModel;
import hzau.sa.backstage.entity.EarlyWarningVO;
import hzau.sa.backstage.service.impl.EarlyWarningServiceImpl;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.LogType;
import hzau.sa.msg.util.ResultUtil;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import hzau.sa.backstage.service.EarlyWarningService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 *  控制器
 * @author haokai
 * @date 2020-08-23
 */
@RestController
@RequestMapping("/earlyWarning")
@Api(value = "-API", tags = { "预警管理" })
public class  EarlyWarningController extends BaseController {

    @Autowired
    private EarlyWarningServiceImpl earlyWarningService;



    @SysLog(prefix = "新增预警", value = LogType.ALL)
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "新增预警" ,notes = "新增预警")
    @ApiImplicitParam(name = "earlyWarningVO",value = "预警实体",paramType = "body",dataType = "EarlyWarningVO")
    @PostMapping("/insert")
    public Result insert(@RequestBody EarlyWarningVO earlyWarningVO){
        boolean save = earlyWarningService.save(earlyWarningVO);
        if(false==save){
            return ResultUtil.databaseError("新增失败");
        }
        return ResultUtil.success("新增成功");
    }

    @SysLog(prefix = "更新预警", value = LogType.ALL)
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "更新预警" ,notes = "更新预警")
    @ApiImplicitParam(name = "earlyWarningVO",value = "预警实体",paramType = "body",dataType = "EarlyWarningVO")
    @PostMapping("/update")
    public Result update(@RequestBody EarlyWarningVO earlyWarningVO){
        boolean save = earlyWarningService.updateById(earlyWarningVO);
        if(false==save){
            return ResultUtil.databaseError("更新失败");
        }
        return ResultUtil.success("更新成功");
    }

    @SysLog(prefix = "删除预警", value = LogType.ALL)
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "删除预警" ,notes = "删除预警")
    @ApiImplicitParam(name = "earlyWarningId",value = "预警id",paramType = "path",dataType = "String")
    @PostMapping("/deleteOne/{earlyWarningId}")
    public Result deleteOne(@PathVariable int earlyWarningId){
        boolean removeById = earlyWarningService.removeById(earlyWarningId);
        if(false==removeById){
            return ResultUtil.databaseError("删除失败");
        }
        return ResultUtil.success("删除成功");
    }




    @SysLog(prefix = "批量删除预警", value = LogType.ALL)
    @ApiOperation(value = "批量删除预警", notes = "批量删除预警")
    @ApiImplicitParam(name = "ids[]", value = "预警id数组", paramType = "query", allowMultiple = true,dataType = "String")
    @PostMapping("/deleteList")
    @Transactional(rollbackFor = Exception.class)
    public Result deleteList(@RequestParam(value = "ids[]") Integer[] ids){
        boolean b = earlyWarningService.removeByIds(Arrays.asList(ids));
        if(false==b){
            return ResultUtil.databaseError();
        }else{
            return ResultUtil.success();
        }
    }


    @ApiOperation("预警分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String"),
    })
    @GetMapping("/page")
    public Result page(){
        Page<EarlyWarningVO> page = getPage();
        IPage<EarlyWarningVO> earlyWarningVOIPage = earlyWarningService.page(page);
        return ResultUtil.success(earlyWarningVOIPage);
    }

    @ApiOperation("预警id,name查询")
    @GetMapping("/getIdAndName")
    public Result getIdAndName(){
        List<EarlyWarningModel> earlyWarningModels = earlyWarningService.selectEarlyWarningModel();
        return ResultUtil.success(earlyWarningModels);
    }
}