package hzau.sa.trainingReport.controller;

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
import pub.ddu.boot.core.annotation.SysLog;
import pub.ddu.boot.framework.controller.BaseController;
import pub.ddu.boot.core.enums.LogType;
import pub.ddu.boot.core.exception.DduException;
import pub.ddu.boot.core.utils.R;
import hzau.sa.trainingReport.entity.TrainingReportVO;
import hzau.sa.trainingReport.service.TrainingReportService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;


/**
 *  控制器
 * @author wuyihu
 * @date 2020-08-19
 */
@RestController
@RequestMapping("sys/trainingReport")
@Api(value = "-API", tags = { "相关接口" })
public class TrainingReportController extends BaseController {

    @Autowired
    private TrainingReportService trainingReportService;

    /**
     * 分页列表
     */
    @ApiOperation("分页查询")
    @ApiImplicitParam(name = "trainingReport", value = "实体", paramType = "query", dataType = "TrainingReportVO")
    @GetMapping("/page")
    public R page(TrainingReportVO trainingReport) {
        Page<TrainingReportVO> page = getPage();
        QueryWrapper<TrainingReportVO> queryWrapper = new QueryWrapper<TrainingReportVO>(trainingReport);
        IPage<TrainingReportVO> pageInfo = trainingReportService.page(page, queryWrapper);
        return R.ok().put("total", pageInfo.getTotal()).put("rows", pageInfo.getRecords());
    }

    //TODO.....
}