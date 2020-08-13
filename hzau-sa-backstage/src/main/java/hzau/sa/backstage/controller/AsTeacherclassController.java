package hzau.sa.backstage.controller;

import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;

import cn.hutool.core.convert.Convert;
import hzau.sa.backstage.entity.ClassGradeModel;
import hzau.sa.backstage.entity.TeacherClassModel;
import hzau.sa.backstage.service.impl.AsTeacherclassServiceImpl;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import hzau.sa.backstage.entity.AsTeacherclassVO;
import hzau.sa.backstage.service.AsTeacherclassService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;


/**
 *  控制器
 * @author haokai
 * @date 2020-08-13
 */
@Slf4j
@RestController
@RequestMapping("/asTeacherclass")
@Api(value = "-API", tags = { "班级老师关系接口" })
public class AsTeacherclassController extends BaseController {

    @Autowired
    private AsTeacherclassServiceImpl asTeacherclassService;

    @ApiOperation(value = "新增班师关系", notes = "新增班师关系")
    @ApiImplicitParam(name = "asTeacherclassVO", value = "班师关系实体", paramType = "body", allowMultiple = true,dataType = "AsTeacherclassVO")
    @PostMapping("/add")
    public Result add(@RequestBody List<AsTeacherclassVO> asTeacherclassVOs) {
        boolean save = asTeacherclassService.saveList(asTeacherclassVOs);
        if (false == save) {
            return ResultUtil.databaseError();
        } else {
            return ResultUtil.success();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "删除班师关系", notes = "删除班师关系")
    @ApiImplicitParam(name = "cropPropertyId", value = "关系id", paramType = "path", dataType = "int")
    @PostMapping("/delete/{asTeacherclassId}")
    public Result delete(@PathVariable("asTeacherclassId") int asTeacherclassId){
        log.info(String.valueOf(asTeacherclassId));
        boolean b = asTeacherclassService.removeById(asTeacherclassId);
        if(false == b){
            return ResultUtil.databaseError(b);
        }else {
            return ResultUtil.success("成功删除");
        }
    }


    @ApiOperation(value = "批量删除班师关系", notes = "批量删除班师关系")
    @ApiImplicitParam(name = "ids", value = "班师关系id数组", paramType = "query", allowMultiple = true,dataType = "Integer")
    @PostMapping("/deleteList")
    @Transactional(rollbackFor = Exception.class)
    public Result deleteList(@RequestParam(value = "ids[]") Integer[] ids){
        boolean b = asTeacherclassService.removeByIds(Arrays.asList(ids));
        if(false==b){
            return ResultUtil.databaseError();
        }else{
            return ResultUtil.success();
        }
    }

    @ApiOperation(value = "按老师id分页查询班师关系", notes = "按老师id分页查询班师关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId", value = "老师id", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "keyword", value = "班级名称关键字", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "gradeName", value = "年级名称", paramType = "query", dataType = "String")
    })
    @GetMapping("/listByTeacherId")
    public Result listByTeacherId(String teacherId,String keyword,String gradeName){
        keyword = Convert.toStr(keyword,"");
        Page<TeacherClassModel> page = getPage();
        List<TeacherClassModel> list = asTeacherclassService.listByTeacherId(page,teacherId,keyword,gradeName);

        return ResultUtil.success(list);
    }


    @ApiOperation(value = "分页查询无老师班级", notes = "查询无老师班级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "班级名称关键字", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "gradeName", value = "年级名称", paramType = "query", dataType = "String")
    })
    @GetMapping("/listClassWithoutTeacher")
    public Result listClassWithoutTeacher(String keyword,String gradeName){
        keyword = Convert.toStr(keyword,"");
        Page<ClassGradeModel> page = getPage();
        List<ClassGradeModel> list = asTeacherclassService.listClassWithoutTeacher(keyword,gradeName);
        return ResultUtil.success(list);
    }

    //TODO.....
}