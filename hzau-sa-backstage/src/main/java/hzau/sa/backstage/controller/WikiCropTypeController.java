package hzau.sa.backstage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.WikiCropTypeDTO;
import hzau.sa.backstage.service.WikiCropTypeService;
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
 * @Description :
 * @date 2020-09-02 13:22
 */
@Slf4j
@RestController
@RequestMapping("/wikiCropType")
@Api(value = "百科植物类型-API", tags = { "百科植物类型接口" })
public class WikiCropTypeController extends BaseController {

    @Resource
    private WikiCropTypeService wikiCropTypeService;

    @ApiOperation("百科植物类型查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "keyword",value = "关键字",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "wikiCategoryId",value = "百科分类ID",paramType = "query",required = true,dataType = "String")
    })
    @GetMapping("/query")
    public Result<Object> queryAllWikiCropType(String keyword,String wikiCategoryId){
        Page<WikiCropTypeDTO> page =  getPage();

        return ResultUtil.success(wikiCropTypeService.queryAllWikiCropType(page,keyword, Integer.valueOf(wikiCategoryId)));
    }

    @SysLog(prefix = "新增百科植物类型",value = LogType.ALL)
    @ApiOperation("新增百科植物类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wikiCategoryId",value = "百科分类Id",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "wikiCropTypeName",value = "百科植物类型名称",paramType = "query",required = true,dataType = "String")
    })
    @PostMapping("/insert")
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> insertWikiCropType(String wikiCategoryId,String wikiCropTypeName){
        Object savePoint = null;
        boolean flag = false;
        try{
            savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
            flag = wikiCropTypeService.insertWikiCropType(Integer.valueOf(wikiCategoryId),wikiCropTypeName);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            return ResultUtil.databaseError(e.toString());
        }
        return ResultUtil.success(flag);
    }

    @SysLog(prefix = "修改百科植物类型",value = LogType.ALL)
    @ApiOperation("修改百科植物类型")
    @ApiImplicitParam(name = "wikiCropTypeDTO",value = "修改百科植物分类实体（需要给出主键）",paramType = "body",dataType = "WikiCropTypeDTO")
    @PutMapping("/update")
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> updateWikiCategory(@Valid @RequestBody WikiCropTypeDTO wikiCropTypeDTO, BindingResult result){
        if(result.hasErrors()){
            return ResultUtil.paramError();
        }else{
            Object savePoint = null;
            boolean flag = false;
            try{
                savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
                flag = wikiCropTypeService.updateWikiCropType(wikiCropTypeDTO);
            }catch (Exception e){
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                return ResultUtil.databaseError(e.toString());
            }
            return ResultUtil.success(flag);
        }
    }

    @SysLog(prefix = "根据主键删除百科植物类型",value = LogType.ALL)
    @ApiOperation("根据主键删除百科植物类型")
    @ApiImplicitParam(name = "id",value = "百科植物类型主键",required = true,paramType = "path",dataType = "String")
    @DeleteMapping("/delete/{id}")
    public Result<Object> deleteById(@PathVariable("id") String id){
        boolean flag = true;
        try{
            log.info("wikiCropType:" + id);
            flag = wikiCropTypeService.removeById(id);
        }catch (Exception e){
            log.error(e.toString());
            return ResultUtil.databaseError(flag);
        }
        if(flag == true){
            return ResultUtil.success(flag);
        }else{
            return ResultUtil.error("百科植物类型不存在");
        }
    }

    @SysLog(prefix = "批量删除百科植物类型",value = LogType.ALL)
    @ApiOperation("批量删除百科植物类型")
    @ApiImplicitParam(name = "ids[]",value = "百科植物分类主键数组",required = true,paramType = "query",allowMultiple = true,dataType = "String")
    @DeleteMapping("/deletes")
    public Result<Object> delete(@RequestParam("ids[]") String[] ids){
        boolean flag = true;
        try{
            log.info("wikiCropType.length:" + ids.length);
            flag = wikiCropTypeService.removeByIds(Arrays.asList(ids));
        }catch (Exception e){
            log.error(e.toString());
            return ResultUtil.databaseError(flag);
        }
        if(flag == true){
            return ResultUtil.success(flag);
        }else{
            return ResultUtil.error("百科分类不存在");
        }
    }

}
