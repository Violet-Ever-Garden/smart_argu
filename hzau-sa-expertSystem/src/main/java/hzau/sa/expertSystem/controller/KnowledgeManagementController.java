package hzau.sa.expertSystem.controller;

import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;

import hzau.sa.msg.controller.BaseController;
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
import hzau.sa.expertSystem.entity.KnowledgeManagementVO;
import hzau.sa.expertSystem.service.KnowledgeManagementService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;


/**
 *  控制器
 * @author wuyihu
 * @date 2020-08-30
 */
@RestController
@RequestMapping("sys/knowledgeManagement")
@Api(value = "-API", tags = { "相关接口" })
public class KnowledgeManagementController extends BaseController {

    @Autowired
    private KnowledgeManagementService knowledgeManagementService;

    /**
     * 分页列表
//     */
//    @ApiOperation("分页查询")
//    @ApiImplicitParam(name = "knowledgeManagement", value = "实体", paramType = "query", dataType = "KnowledgeManagementVO")
//    @GetMapping("/page")

}