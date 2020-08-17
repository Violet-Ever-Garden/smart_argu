package hzau.sa.backstage.controller;

import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;

import cn.hutool.core.convert.Convert;
import hzau.sa.backstage.entity.CropVO;
import hzau.sa.backstage.entity.FieldWrapper;
import hzau.sa.backstage.service.impl.FieldServiceImpl;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.LogType;
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
@Slf4j
@RestController
@RequestMapping("/field")
@Api(value = "-API", tags = { "地块接口" })
public class FieldController extends BaseController {

    @Autowired
    private FieldServiceImpl fieldService;

    @SysLog(prefix = "增加地块",value = LogType.ALL)
    @ApiOperation("增加地块")
    @ApiImplicitParam(name = "field",value = "增加的地块",paramType ="body",dataType = "FieldWrapper")
    @PostMapping("/addField")
    public Result addField(@RequestBody FieldWrapper field){
        return fieldService.addField(field);
    }


    @SysLog(prefix = "删除地块",value = LogType.ALL)
    @ApiOperation("删除地块")
    @ApiImplicitParam(name = "fieldId",value = "删除地块的id",paramType = "query",dataType = "String")
    @PostMapping("/deleteField")
    public Result deleteField(String fieldId){
        return fieldService.deleteField(Integer.valueOf(fieldId));
    }


    @SysLog(prefix = "批量删除地块",value = LogType.ALL)
    @ApiOperation("批量删除地块")
    @ApiImplicitParam(name="fieldIds",value = "删除地块的id",paramType = "query",allowMultiple = true,dataType = "Integer")
    @PostMapping("/deleteFields")
    public Result deleteFields(Integer[] fieldIds){return fieldService.deleteFields(fieldIds);}


    @SysLog(prefix = "更新地块",value = LogType.ALL)
    @ApiOperation("更新地块")
    @ApiImplicitParam(name = "field",value = "更新地块",paramType = "body",dataType = "FieldWrapper")
    @PostMapping("/updateField")
    public Result updateField(@RequestBody FieldWrapper field){
        return fieldService.updateField(field);
    }


    /**
     * 这个地方需要修改一下返回的不能是regionId，但是返回的要按照具体的需求，这个还不是很明确
     * @param keyword
     * @return
     */
    @ApiOperation(value = "按名字分页模糊查询", notes = "按名字分页模糊查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String"),

    })
    @GetMapping("/page")
    public Result page(String keyword){
        keyword = Convert.toStr(keyword,"");
        Page<FieldVO> page = getPage();
        log.info(keyword);
        log.info(page.toString());
        QueryWrapper<FieldVO> queryWrapper = new QueryWrapper<FieldVO>();
        queryWrapper.like("fieldName",keyword)
                .orderByAsc("createTime");
        return ResultUtil.success(fieldService.page(page,queryWrapper));
    }
}