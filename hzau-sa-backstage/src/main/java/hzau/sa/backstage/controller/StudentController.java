package hzau.sa.backstage.controller;

import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;

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

import hzau.sa.backstage.entity.StudentVO;
import hzau.sa.backstage.service.StudentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;


/**
 *  控制器
 * @author wuyihu
 * @date 2020-08-13
 */
@RestController
@RequestMapping("sys/student")
@Api(value = "-API", tags = { "相关接口" })
public class StudentController{

    @Autowired
    private StudentService studentService;

    /**
     * 分页列表
     */

    /**
     * 返回所有年级
     */

    /**
     * 按年级分页
     */

    /**
     * 返回所有班级
     */

    /**
     * 按班级分页
     */

    /**
     * 按名字查找分页
     */

    /**
     * 添加学生
     */

    /**
     * 从模板中添加学生
     */

    /**
     * 删除学生
     */


    /**
     * 更新学生
     */

    /**
     * 模板下载
     */

}