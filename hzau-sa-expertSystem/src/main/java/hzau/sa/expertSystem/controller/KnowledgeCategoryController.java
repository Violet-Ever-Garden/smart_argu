package hzau.sa.expertSystem.controller;

import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;

import cn.hutool.core.convert.Convert;
import hzau.sa.expertSystem.service.impl.KnowledgeCategoryServiceImpl;
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
import hzau.sa.expertSystem.entity.KnowledgeCategoryVO;
import hzau.sa.expertSystem.service.KnowledgeCategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;


/**
 *  控制器
 * @author lvhao
 * @date 2020-08-29
 */
@Slf4j
@RestController
@RequestMapping("/knowledgeCategory")
@Api(value = "-API", tags = { "相关接口" })
public class KnowledgeCategoryController extends BaseController {

    @Autowired
    private KnowledgeCategoryServiceImpl knowledgeCategoryService;


    /**
     * 增加分类
     * @param
     * @return
     */
    @SysLog(prefix = "增加分类")
    @ApiOperation("增加分类")
    @ApiImplicitParam(name = "knowledgeCategoryVO", value = "分类", paramType = "body", dataType = "KnowledgeCategoryVO")
    @PostMapping("/addCategory")
    public Result addCategory(@RequestBody KnowledgeCategoryVO knowledgeCategoryVO){
        return knowledgeCategoryService.addCategory(knowledgeCategoryVO);
    }

    /**
     * 更新分类
     * @param knowledgeCategoryVO
     * @return
     */
    @SysLog(prefix = "更新分类")
    @ApiOperation("更新分类")
    @ApiImplicitParam(name = "knowledgeCategoryVO", value = "分类", paramType = "body", dataType = "KnowledgeCategoryVO")
    @PostMapping("/updateCategory")
    public Result updateCategory(@RequestBody KnowledgeCategoryVO knowledgeCategoryVO){
        return knowledgeCategoryService.updateCategory(knowledgeCategoryVO);
    }

    /**
     * 批量删除分类
     * @param categoryIds
     * @return
     */
    @SysLog(prefix = "批量删除分类")
    @ApiOperation("批量删除分类")
    @ApiImplicitParam(name = "categoryIds", value = "分类ids", paramType = "query",allowMultiple = true, dataType = "Integer")
    @PostMapping("/deleteCatagories")
    public Result deleteCatagories(Integer[] categoryIds){
        return knowledgeCategoryService.deleteCategories(categoryIds);
    }


    @SysLog(prefix = "按名字分页")
    @ApiOperation("按名字分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字，默认为空时返回全部", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String"),
    })
    @GetMapping("/page")
    public Result page(String name){
        name= Convert.toStr(name,"");
        Page<KnowledgeCategoryVO> page = getPage();
        QueryWrapper<KnowledgeCategoryVO> knowledgeCategoryVOQueryWrapper = new QueryWrapper<>();
        knowledgeCategoryVOQueryWrapper.lambda().like(KnowledgeCategoryVO::getKnowledgeCategoryName,name)
                .orderByDesc(KnowledgeCategoryVO::getCreateTime);
        return ResultUtil.success(knowledgeCategoryService.page(page,knowledgeCategoryVOQueryWrapper));
    }
}