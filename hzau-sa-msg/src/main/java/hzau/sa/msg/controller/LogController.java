package hzau.sa.msg.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.msg.entity.LogVO;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.service.LogService;
import hzau.sa.msg.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LvHao
 * @Description : 日志管理控制器
 * @date 2020-08-09 15:27
 */
@RestController
@Api(value = "日志管理-API", tags = { "日志管理相关接口" })
@RequestMapping("/log")
public class LogController extends BaseController{

    @Resource
    private LogService logService;

    /**
     * 日志管理分页列表
     *
     * @param username
     * @param time
     * @param retCode
     * @return
     */
    @ApiOperation("分页查询日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "time", value = "执行时长", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "retCode", value = "返回码", paramType = "query", dataType = "String") })
    @GetMapping("/page")
    public Result<Object> page(String username, String time, String retCode){
        Page<LogVO> page = getPage();
        QueryWrapper<LogVO> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(username)) {
            queryWrapper.lambda().eq(LogVO::getUsername, username);
        }
        if (StrUtil.isNotBlank(time)) {
            queryWrapper.lambda().gt(LogVO::getTime, time);
        }
        if (StrUtil.isNotBlank(retCode)) {
            queryWrapper.lambda().eq(LogVO::getRetCode, retCode);
        }
        queryWrapper.lambda().orderByDesc(LogVO::getCreateTime);
        IPage<LogVO> pageInfo = logService.page(page,queryWrapper);

        Map map = new HashMap<>();
        if(null != pageInfo){
            map.put("total",pageInfo.getTotal());
            map.put("rows",pageInfo.getRecords());
        }else{
            map.put("error","数据库为空");
        }

        return ResultUtil.success(map);
    }

}
