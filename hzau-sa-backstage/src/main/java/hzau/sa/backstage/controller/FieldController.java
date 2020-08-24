package hzau.sa.backstage.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.FieldModel;
import hzau.sa.backstage.entity.FieldWrapper;
import hzau.sa.backstage.service.impl.FieldServiceImpl;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
     * @param fieldName
     * @return
     */
    @ApiOperation(value = "按名字分页模糊查询", notes = "按名字分页模糊查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fieldName", value = "关键字", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String"),

    })
    @GetMapping("/page")
    public Result page(String fieldName){
        fieldName=Convert.toStr(fieldName,"");
        Page<FieldModel> page = getPage();
        IPage<FieldModel> iPage = fieldService.page(page, fieldName);
        return ResultUtil.success(iPage);
    }
}