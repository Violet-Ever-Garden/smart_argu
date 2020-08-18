package hzau.sa.backstage.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.ClassManage;
import hzau.sa.backstage.entity.ClassVO;
import hzau.sa.backstage.entity.GradeVO;
import hzau.sa.backstage.service.ClassService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;

/**
 * @author LvHao
 * @Description : 班级管理控制器
 * @date 2020-08-12 18:12
 */
@Slf4j
//@RestController
@RequestMapping("/class")
@Api(value = "班级管理-API",tags = "班级管理相关接口")
public class ClassManageController extends BaseController {

    @Resource
    private ClassService classService;

    @Resource
    private GradeService gradeService;

    /**
     * 班级管理分页查询
     * @param grade
     * @param name
     * @return
     */
    @ApiOperation("班级管理查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "grade",value = "年级",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "name",value = "名称",paramType = "query",dataType = "String")
    })
    @GetMapping("/query")
    public Result<Object> queryClass(String grade,String name){

        Page page = getPage();

        QueryWrapper<ClassVO> queryWrapper = new QueryWrapper<>();
        if(StrUtil.isNotBlank(name)){
            queryWrapper.lambda().like(ClassVO::getClassName,name);
        }
        if(StrUtil.isNotBlank(grade)){
            try{
                Integer gradeId = gradeService.getOne(new QueryWrapper<GradeVO>().lambda().eq(GradeVO::getGradeName,grade)).getGradeId();
                queryWrapper.lambda().eq(ClassVO::getGradeId,gradeId);
            }catch (NullPointerException e){
                return ResultUtil.error("没有该年级");
            }
        }

        return ResultUtil.success(classService.findClass(page,queryWrapper));
    }

    /**
     * 所有存在班级查询
     * @return
     */
    @ApiOperation("所有存在班级查询")
    @GetMapping("/allClass")
    public Result<Object> queryAllClass(){
        return ResultUtil.success(classService.queryAllClass());
    }

    /**
     * 班级所属地块查询
     * @param classId
     * @return
     */
    @ApiOperation("班级所属地块查询")
    @ApiImplicitParam(name = "classId",value = "班级ID",required = true,paramType = "query",dataType = "String")
    @GetMapping("/fields")
    public Result<Object> queryClassFields(Integer classId){
        if(null != classId){
            return ResultUtil.success(classService.classFields(classId));
        }else{
            return ResultUtil.paramError();
        }
    }

    /**
     * 班级所属视频监控查询
     * @param classId
     * @return
     */
    @ApiOperation("班级所属视频监控查询")
    @ApiImplicitParam(name = "classId",value = "班级ID",required = true,paramType = "query",dataType = "String")
    @GetMapping("/monitors")
    public Result<Object> queryClassMonitors(Integer classId){
        if(null != classId){
            return ResultUtil.success(classService.classMonitors(classId));
        }else{
            return ResultUtil.paramError();
        }
    }

    /**
     * 修改班级信息
     * @param classManage
     * @param result
     * @return
     */
    @SysLog(prefix = "修改班级信息",value = LogType.ALL)
    @ApiOperation("修改班级信息")
    @ApiImplicitParam(name = "classManage",value = "修改信息实体",paramType = "body",dataType = "ClassManage")
    @PutMapping("/update")
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> updateClassMessage(@Valid @RequestBody ClassManage classManage, BindingResult result){
        if(result.hasErrors()){
            return ResultUtil.paramError();
        }else{
            Object savePoint = null;
            ClassManage classManageResult = null;
            try{
                savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
                classManageResult = classService.updateClassMessage(classManage);
            }catch (Exception e){
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                return ResultUtil.databaseError("修改失败");
            }
            return ResultUtil.success(classManageResult);
        }
    }

    /**
     * 新增班级信息
     * @param classManage
     * @param result
     * @return
     */
    @SysLog(prefix = "新增班级信息",value = LogType.ALL)
    @ApiOperation("新增班级信息")
    @ApiImplicitParam(name = "classManage",value = "新增信息实体(主键自增）",paramType = "body",dataType = "ClassManage")
    @PostMapping("/insert")
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> insertClassMessage(@Valid @RequestBody ClassManage classManage, BindingResult result){
        if(result.hasErrors()){
            return ResultUtil.paramError();
        }else{
            Object savePoint = null;
            ClassManage classManageResult = null;
            try{
                savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
                classManageResult = classService.insertClassMessage(classManage);
            }catch (Exception e){
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                return ResultUtil.databaseError("插入失败");
            }
            return ResultUtil.success(classManageResult);
        }
    }

    /**
     * 根据主键删除班级
     * @param id
     * @return
     */
    @SysLog(prefix = "根据主键删除班级",value = LogType.ALL)
    @ApiOperation("根据主键删除班级")
    @ApiImplicitParam(name = "id",value = "班级主键",required = true,paramType = "path",dataType = "String")
    @DeleteMapping("/delete/{id}")
    public Result<Object> deleteById(@PathVariable("id") String id){
        boolean flag = true;
        try{
            log.info("classId:" + id);
            flag = classService.removeById(id);
        }catch (Exception e){
            log.error(e.toString());
            return ResultUtil.databaseError(flag);
        }
        if(flag == true){
            return ResultUtil.success(flag);
        }else{
            return ResultUtil.error("班级不存在");
        }
    }

    /**
     * 批量删除班级
     * @param ids
     * @return
     */
    @SysLog(prefix = "批量删除班级",value = LogType.ALL)
    @ApiOperation("批量删除班级")
    @ApiImplicitParam(name = "ids[]",value = "班级主键数组",required = true,paramType = "query",allowMultiple = true,dataType = "String")
    @DeleteMapping("/deletes")
    public Result<Object> delete(@RequestParam("ids[]") String[] ids){
        boolean flag = true;
        try{
            log.info("classIds.length:" + ids.length);
            flag = classService.removeByIds(Arrays.asList(ids));
        }catch (Exception e){
            log.error(e.toString());
            return ResultUtil.databaseError(flag);
        }
        if(flag == true){
            return ResultUtil.success(flag);
        }else{
            return ResultUtil.error("班级不存在");
        }
    }
}
