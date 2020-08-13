package hzau.sa.backstage.controller;

import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;

import hzau.sa.msg.annotation.SysLog;
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
@RequestMapping("sys/field")
@Api(value = "-API", tags = { "地块接口" })
public class FieldController{

    @Autowired
    private FieldService fieldService;

    /**
     * 分页列表
     */
    @SysLog(prefix = "分页查询地块",value = LogType.ALL)
    @ApiOperation("分页查询地块")
    @ApiImplicitParam(name = "current", value = "请求的页数", paramType = "query", dataType = "int")
    @PostMapping("/page")
    public Result page(@RequestParam(value = "pageNo",required = true) int pageNo) {
        return fieldService.page(pageNo);
    }

    @SysLog(prefix = "增加地块",value = LogType.ALL)
    @ApiOperation("增加地块")
    @ApiImplicitParam(name = "field",value = "增加的地块",dataType = "FieldVO")
    @PostMapping("/addField")
    public Result addField(FieldVO field){
        return fieldService.addField(field);
    }

    @SysLog(prefix = "删除地块",value = LogType.ALL)
    @ApiOperation("删除地块")
    @ApiImplicitParam(name = "fieldId",value = "删除地块的id",dataType = "Integer")
    @PostMapping("/deleteField")
    public Result deleteField(Integer fieldId){
        return fieldService.deleteField(fieldId);
    }

    @SysLog(prefix = "批量删除地块",value = LogType.ALL)
    @ApiOperation("批量删除地块")
    @ApiImplicitParam(name="fieldIds",value = "删除地块的id",allowMultiple = true,dataType = "Integer")
    @PostMapping("/deleteFields")
    public Result deleteFields(Integer[] fieldIds){return fieldService.deleteFields(fieldIds);}

    @SysLog(prefix = "更新地块",value = LogType.ALL)
    @ApiOperation("更新地块")
    @ApiImplicitParam(name = "field",value = "更新地块",dataType = "FieldVO")
    @PostMapping("/updateField")
    public Result updateField(FieldVO field){
        return fieldService.updateField(field);
    }

    @SysLog(prefix = "查找地块",value = LogType.ALL)
    @ApiOperation("查找地块")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "fieldName",value = "查找地块的名字",dataType = "String"),
    @ApiImplicitParam(name="pageNo",value = "要显示的页面",dataType = "int")
    })
    @PostMapping("/findField")
    public Result findField(@RequestParam(value = "fieldName",required = true) String fieldName,@RequestParam(value = "pageNo",required = true) int pageNo){
        return fieldService.findField(fieldName,pageNo);
    }
}