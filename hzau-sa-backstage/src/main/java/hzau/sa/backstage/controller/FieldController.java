package hzau.sa.backstage.controller;

import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;

import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
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
import hzau.sa.backstage.entity.FieldVO;
import hzau.sa.backstage.service.FieldService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;


/**
 *  控制器
 * @author wuyihu
 * @date 2020-08-12
 */
@RestController
@RequestMapping("sys/field")
@Api(value = "地块管理-API", tags = { "地块管理相关接口" })
public class FieldController{

    @Autowired
    private FieldService fieldService;

    /**
     * 分页列表
     */
    @ApiOperation("分页查询地块")
    @ApiImplicitParam(name = "current", value = "请求的页数", paramType = "query", dataType = "String")
    @GetMapping("/page")
    public Result page(@RequestParam(value = "pageNo",required = true) String pageNo) {
        return fieldService.page(Integer.parseInt(pageNo));
    }

    @ApiOperation("增加地块")
    @ApiImplicitParam(name = "field",value = "增加的地块",dataType = "FieldVO")
    @GetMapping("/addField")
    public Result addField(FieldVO field){
        return fieldService.addField(field);
    }

    @ApiOperation("删除地块")
    @ApiImplicitParam(name = "fieldId",value = "删除地块的id",dataType = "String")
    @GetMapping("/deleteField")
    public Result deleteField(String fieldId){
        return fieldService.deleteField(fieldId);
    }

    @ApiOperation("更新地块")
    @ApiImplicitParam(name = "field",value = "更新地块",dataType = "FieldVO")
    @GetMapping("/updateField")
    public Result updateField(FieldVO field){
        return fieldService.updateField(field);
    }

    @ApiOperation("查找地块")
    @ApiImplicitParam(name = "fieldName",value = "查找地块的名字",dataType = "String")
    @GetMapping("/findField")
    public Result findField(@RequestParam(value = "fieldName",required = true) String fieldName,@RequestParam(value = "pageNo",required = true) int pageNo){
        return fieldService.findField(fieldName,pageNo);
    }
}