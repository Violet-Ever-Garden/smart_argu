package hzau.sa.backstage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.VideoMonitorDTO;
import hzau.sa.backstage.entity.VideoMonitorModel;
import hzau.sa.backstage.entity.WikiCategoryDTO;
import hzau.sa.backstage.service.WikiCategoryService;
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
 * @date 2020-09-02 10:59
 */
@Slf4j
@RestController
@RequestMapping("/wikiCategory")
@Api(value = "百科分类-API", tags = { "百科分类接口" })
public class WikiCategoryController extends BaseController {

    @Resource
    private WikiCategoryService wikiCategoryService;

    @ApiOperation("百科分类查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "keyword",value = "关键字",paramType = "query",dataType = "String")
    })
    @GetMapping("/query")
    public Result<Object> queryAllWikiCategory(String keyword){
        Page<WikiCategoryDTO> page =  getPage();

        return ResultUtil.success(wikiCategoryService.queryAllWikiCategory(page,keyword));
    }

    @SysLog(prefix = "新增百科分类",value = LogType.ALL)
    @ApiOperation("新增百科分类")
    @ApiImplicitParam(name = "wikiCategoryName",value = "百科分类名称",paramType = "query",required = true,dataType = "String")
    @PostMapping("/insert")
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> insertWikiCategory(String wikiCategoryName){
        Object savePoint = null;
        boolean flag = false;
        try{
            savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
            flag = wikiCategoryService.insertWikiCategory(wikiCategoryName);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            return ResultUtil.databaseError(e.toString());
        }
        return ResultUtil.success(flag);
    }

    @SysLog(prefix = "修改百科分类",value = LogType.ALL)
    @ApiOperation("修改百科分类")
    @ApiImplicitParam(name = "wikiCategoryDTO",value = "修改百科分类实体（需要给出主键）",paramType = "body",dataType = "WikiCategoryDTO")
    @PutMapping("/update")
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> updateWikiCategory(@Valid @RequestBody WikiCategoryDTO wikiCategoryDTO, BindingResult result){
        if(result.hasErrors()){
            return ResultUtil.paramError();
        }else{
            Object savePoint = null;
            boolean flag = false;
            try{
                savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
                flag = wikiCategoryService.updateWikiCategoryById(wikiCategoryDTO);
            }catch (Exception e){
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                return ResultUtil.databaseError(e.toString());
            }
            return ResultUtil.success(flag);
        }
    }

    @SysLog(prefix = "根据主键删除百科分类",value = LogType.ALL)
    @ApiOperation("根据主键删除百科分类")
    @ApiImplicitParam(name = "id",value = "百科分类主键",required = true,paramType = "path",dataType = "String")
    @DeleteMapping("/delete/{id}")
    public Result<Object> deleteById(@PathVariable("id") String id){
        boolean flag = true;
        try{
            log.info("wikiCategoryId:" + id);
            flag = wikiCategoryService.removeById(id);
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

    @SysLog(prefix = "批量删除百科分类",value = LogType.ALL)
    @ApiOperation("批量删除百科分类")
    @ApiImplicitParam(name = "ids[]",value = "百科分类主键数组",required = true,paramType = "query",allowMultiple = true,dataType = "String")
    @DeleteMapping("/deletes")
    public Result<Object> delete(@RequestParam("ids[]") String[] ids){
        boolean flag = true;
        try{
            log.info("wikiCategoryIds.length:" + ids.length);
            flag = wikiCategoryService.removeByIds(Arrays.asList(ids));
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
