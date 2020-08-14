package hzau.sa.backstage.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.GradeVO;
import hzau.sa.backstage.service.GradeService;
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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LvHao
 * @Description : 年级管理
 * @date 2020-08-12 20:54
 */
@Slf4j
@RestController
@RequestMapping("/grade")
@Api(value = "年级管理-API",tags = "年级管理相关接口")
public class GradeManageController extends BaseController {

    @Resource
    private GradeService gradeService;

    /**
     * 模糊查找
     * @param name
     * @return
     */
    @ApiOperation("年级管理查询")
    @GetMapping("/query")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "要查询班级名称",paramType = "query",dataType = "String")
    })
    public Result<Object> queryGrade(String name){

        Page page = getPage();

        QueryWrapper<GradeVO> queryWrapper = new QueryWrapper<>();
        if(StrUtil.isNotBlank(name)){
            queryWrapper.lambda().like(GradeVO::getGradeName,name);
        }

        IPage pageInfo = gradeService.page(page,queryWrapper);

        Map result = new HashMap();
        result.put("total",pageInfo.getTotal());
        result.put("rows",pageInfo.getRecords());

        return ResultUtil.success(result);
    }

    @ApiOperation("所有存在年级查询")
    @GetMapping("/allGrade")
    public Result<Object> queryAllGrade(){
        return ResultUtil.success(gradeService.queryAllGrade());
    }

    /**
     * 按照主键修改
     * @param gradeVO
     * @return
     */
    @SysLog(prefix = "修改年级",value = LogType.ALL)
    @ApiOperation("修改年级")
    @ApiImplicitParam(name = "gradeVO",value = "年级实体(设置了主键自增 实体里可以不给出id）",paramType = "body",dataType = "GradeVO")
    @PutMapping("/update")
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> updateGrade(@RequestBody GradeVO gradeVO){
        Object savePoint = null;
        try{
            savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
            gradeService.updateById(gradeVO);
        }catch (Exception e){
            log.error(e.toString());
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            return ResultUtil.databaseError("修改失败");
        }
        return ResultUtil.success("修改成功！");
    }

    /**
     * 新增年级
     *
     * 设置了事务回滚的断点
     * @param gradeVO
     * @param result
     * @return
     */
    @SysLog(prefix = "插入年级",value = LogType.ALL)
    @ApiOperation("插入年级")
    @ApiImplicitParam(name = "gradeVO",value = "年级实体",paramType = "body",dataType = "GradeVO")
    @PostMapping("/insert")
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> insertGrade(@Valid @RequestBody GradeVO gradeVO, BindingResult result){
        if(result.hasErrors()){
            return ResultUtil.paramError();
        }else{
            Object savePoint = null;
            try{
                savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
                gradeService.save(gradeVO);
            }catch (DuplicateKeyException e){
                log.error(e.toString());
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                return ResultUtil.databaseError("主键重复");
            }
            return ResultUtil.success("插入数据成功");
        }
    }

    /**
     * 根据主键删除年级
     * @param id
     * @return
     */
    @SysLog(prefix = "根据主键删除年级",value = LogType.ALL)
    @ApiOperation("根据主键删除年级")
    @ApiImplicitParam(name = "id",value = "年级主键",required = true,paramType = "path",dataType = "String")
    @DeleteMapping("/delete/{id}")
    public Result<Object> deleteById(@PathVariable("id") String id){
        boolean flag = true;
        try{
            log.info("gradeId:" + id);
            flag = gradeService.removeById(id);
        }catch (Exception e){
            log.error(e.toString());
            return ResultUtil.databaseError(flag);
        }
        if(flag == true){
            return ResultUtil.success(flag);
        }else{
            return ResultUtil.error("年级不存在");
        }
    }

    /**
     * 批量删除年级
     * @param ids
     * @return
     */
    @SysLog(prefix = "批量删除年级",value = LogType.ALL)
    @ApiOperation("批量删除年级")
    @ApiImplicitParam(name = "ids[]",value = "年级主键数组",required = true,paramType = "query",allowMultiple = true,dataType = "String")
    @DeleteMapping("/deletes")
    public Result<Object> delete(@RequestParam("ids[]") String[] ids){
        boolean flag = true;
        try{
            log.info("gradeIds.length:" + ids.length);
            flag = gradeService.removeByIds(Arrays.asList(ids));
        }catch (Exception e){
            log.error(e.toString());
            return ResultUtil.databaseError(flag);
        }
        if(flag == true){
            return ResultUtil.success(flag);
        }else{
            return ResultUtil.error("年级不存在");
        }
    }

}
