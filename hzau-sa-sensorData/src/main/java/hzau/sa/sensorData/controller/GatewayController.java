package hzau.sa.sensorData.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.LogType;
import hzau.sa.msg.util.ResultUtil;
import hzau.sa.sensorData.entity.GatewayDTO;
import hzau.sa.sensorData.entity.GatewayVO;
import hzau.sa.sensorData.service.GatewayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;

/**
 * @author LvHao
 * @Description :
 * @date 2020-09-03 21:48
 */
@Slf4j
@RestController
@RequestMapping("/gateway")
@Api(value = "网关设置-API",tags = "网关设置相关接口")
public class GatewayController extends BaseController {

    @Resource
    private GatewayService gatewayService;

    @ApiOperation("网关设置查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String")
    })
    @GetMapping("/query")
    public Result<Object> queryAllVideoGateway(){
        Page<GatewayDTO> page =  getPage();

        return ResultUtil.success(gatewayService.queryGateway(page));
    }

    @SysLog(prefix = "新增网关设置",value = LogType.ALL)
    @ApiOperation("新增网关设置")
    @ApiImplicitParam(name = "gatewayDTO",value = "新增网关设置实体(主键自增）",paramType = "body",dataType = "GatewayDTO")
    @PostMapping("/insert")
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> insetGateway(@Valid @RequestBody GatewayDTO gatewayDTO, BindingResult result){
        if(result.hasErrors()){
            return ResultUtil.paramError();
        }else{
            Object savePoint = null;
            boolean flag = false;
            try{
                savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();

                GatewayVO gatewayVO = new GatewayVO();
                gatewayVO.setGatewayName(gatewayDTO.getGatewayName());
                gatewayVO.setGatewayAddress(gatewayDTO.getGatewayAddress());

                flag = gatewayService.save(gatewayVO);
            }catch (Exception e){
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                return ResultUtil.databaseError(e.toString());
            }
            return ResultUtil.success(flag);
        }
    }

    @SysLog(prefix = "修改网关设置",value = LogType.ALL)
    @ApiOperation("修改网关设置")
    @ApiImplicitParam(name = "gatewayDTO",value = "修改网关设置实体(主键自增）",paramType = "body",dataType = "GatewayDTO")
    @PutMapping("/update")
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> updateGateway(@Valid @RequestBody GatewayDTO gatewayDTO, BindingResult result){
        if(result.hasErrors()){
            return ResultUtil.paramError();
        }else{
            Object savePoint = null;
            boolean flag = false;
            try{
                savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();

                GatewayVO gatewayVO = new GatewayVO();
                gatewayVO.setGatewayId(gatewayDTO.getGatewayId());
                gatewayVO.setGatewayName(gatewayDTO.getGatewayName());
                gatewayVO.setGatewayAddress(gatewayDTO.getGatewayAddress());

                flag = gatewayService.updateById(gatewayVO);
            }catch (Exception e){
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                return ResultUtil.databaseError(e.toString());
            }
            return ResultUtil.success(flag);
        }
    }

    @SysLog(prefix = "根据主键删除网关设置",value = LogType.ALL)
    @ApiOperation("根据主键删除网关设置")
    @ApiImplicitParam(name = "id",value = "网关主键",required = true,paramType = "path",dataType = "String")
    @DeleteMapping("/delete/{id}")
    public Result<Object> deleteById(@PathVariable("id") String id){
        boolean flag = true;
        try{
            log.info("gatewayId:" + id);
            flag = gatewayService.removeById(id);
        }catch (Exception e){
            log.error(e.toString());
            return ResultUtil.databaseError(flag);
        }
        if(flag == true){
            return ResultUtil.success(flag);
        }else{
            return ResultUtil.error("网关不存在");
        }
    }

    @SysLog(prefix = "批量删除网关设置",value = LogType.ALL)
    @ApiOperation("批量删除网关设置")
    @ApiImplicitParam(name = "ids[]",value = "网关主键数组",required = true,paramType = "query",allowMultiple = true,dataType = "String")
    @DeleteMapping("/deletes")
    public Result<Object> delete(@RequestParam("ids[]") String[] ids){
        boolean flag = true;
        try{
            log.info("gatewayIds.length:" + ids.length);
            flag = gatewayService.removeByIds(Arrays.asList(ids));
        }catch (Exception e){
            log.error(e.toString());
            return ResultUtil.databaseError(flag);
        }
        if(flag == true){
            return ResultUtil.success(flag);
        }else{
            return ResultUtil.error("网关不存在");
        }
    }
}
