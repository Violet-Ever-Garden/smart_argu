package hzau.sa.expertSystem.controller;

import cn.hutool.core.convert.Convert;
import hzau.sa.expertSystem.entity.KnowledgeManagementModel;
import hzau.sa.expertSystem.entity.KnowledgeManagementView;
import hzau.sa.expertSystem.service.impl.KnowledgeManagementServiceImpl;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.FileEnum;
import hzau.sa.msg.util.ResultUtil;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;


/**
 *  控制器
 * @author wuyihu
 * @date 2020-08-30
 */
@Slf4j
@RestController
@RequestMapping("/knowledgeManagement")
@Api(value = "-API", tags = { "知识库管理" })
public class KnowledgeManagementController extends BaseController {

    @Autowired
    private KnowledgeManagementServiceImpl knowledgeManagementService;

    /**
     * 增加知识库
     * @param knowledgeManageName
     * @param knowledgeCategoryName
     * @param knowledgeIntroduction
     * @param knowledgeContentHtml
     * @param file
     * @return
     */
    @SysLog(prefix = "增加知识库")
    @ApiOperation("增加知识库")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "knowledgeManageName",value = "知识库标题",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "knowledgeCategoryName",value = "知识库分类",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "knowledgeIntroduction",value = "知识库简介",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "knowledgeContentHtml",value = "知识库富文本内容",paramType = "query",required = true,dataType = "String")
    })
    @PostMapping("/addKnowledge")
    public Result addKnowledge(String knowledgeManageName, String knowledgeCategoryName, String knowledgeIntroduction, String knowledgeContentHtml, @RequestParam MultipartFile file){
        return knowledgeManagementService.addKnowledge(knowledgeManageName,knowledgeCategoryName,knowledgeIntroduction,knowledgeContentHtml,file);
    }

    /**
     * 更新知识库
     * @param knowledgeManageId
     * @param knowledgeManageName
     * @param knowledgeCategoryName
     * @param knowledgeIntroduction
     * @param knowledgeContentHtml
     * @param file
     * @return
     */
    @SysLog(prefix = "更新知识库")
    @ApiOperation("更新知识库")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "knowledgeManageId",value = "知识库id",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "knowledgeManageName",value = "知识库标题",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "knowledgeCategoryName",value = "知识库分类",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "knowledgeIntroduction",value = "知识库简介",paramType = "query",required = true,dataType = "String"),
            @ApiImplicitParam(name = "knowledgeContentHtml",value = "知识库富文本内容",paramType = "query",required = true,dataType = "String")
    })
    @PostMapping("/updateKnowledge")
    public Result updateKnowledge(String knowledgeManageId,String knowledgeManageName,String knowledgeCategoryName,String knowledgeIntroduction,String knowledgeContentHtml,@RequestParam MultipartFile file){
        return knowledgeManagementService.updateKnowledge(Integer.valueOf(knowledgeManageId),knowledgeManageName,knowledgeCategoryName,knowledgeIntroduction,knowledgeContentHtml,file);
    }

    /**
     * 批量删除知识库
     * @param knowledgeManageIds
     * @return
     */
    @SysLog(prefix = "批量删除知识库")
    @ApiOperation("批量删除知识库")
    @ApiImplicitParam(name = "knowledgeManageIds",value = "知识库的ids",paramType = "query",allowMultiple = true,dataType = "String")
    @GetMapping("/deleteKnowledges")
    public Result deleteKnowledges(String[] knowledgeManageIds){
        return knowledgeManagementService.deleteKnowledges(knowledgeManageIds);
    }


    /**
     * 分页知识库
     * @param knowledgeName
     * @param categoryName
     * @return
     */
    @SysLog(prefix = "分页知识库")
    @ApiOperation("分页知识库")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "knowledgeName",value = "知识库名字关键字",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "categoryName",value = "知识库分类",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String"),
    })
    @GetMapping("/page")
    public Result page(String knowledgeName,String categoryName){
        knowledgeName= Convert.toStr(knowledgeName,"");

        Page<KnowledgeManagementModel> page = getPage();
        IPage<KnowledgeManagementModel> iPage = knowledgeManagementService.page(page, knowledgeName, categoryName);
        return ResultUtil.success(iPage);
    }

    /**
     * 按id返回知识库
     * @param knowledgeManageId
     * @return
     */
    @SysLog(prefix = "按id返回知识库")
    @ApiOperation("按id返回知识库")
    @ApiImplicitParam(name = "knowledgeManageId",value = "知识库id",required = true,paramType = "query",dataType = "String")
    @PostMapping("/queryKnowledgeById")
    public Result queryKnowledgeById(String knowledgeManageId){
        KnowledgeManagementView knowledgeManagementView = knowledgeManagementService.queryKnowledgeById(Integer.valueOf(knowledgeManageId));
        log.debug(knowledgeManagementView.toString());
        return ResultUtil.success(knowledgeManagementView);
    }
}