package hzau.sa.backstage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.VideoMonitorDTO;
import hzau.sa.backstage.entity.VideoMonitorModel;
import hzau.sa.backstage.service.VideoMonitorService;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.LogType;
import hzau.sa.msg.util.FileUtil;
import hzau.sa.msg.util.ResultUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;

/**
 * @author LvHao
 * @Description :
 * @date 2020-08-30 17:05
 */
@Slf4j
@RestController
@RequestMapping("/videoMonitor")
@Api(value = "视频监控设置-API",tags = "视频监控相关接口")
public class VideoMonitorController extends BaseController {

    private static final int BUFFER_SIZE = 2 * 1024;

    @Resource
    private VideoMonitorService videoMonitorService;

    @ApiOperation("视频监控设置查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "keyword",value = "名称和编号关键字",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "baseName",value = "基地名称",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "regionName",value = "区域名称",paramType = "query",dataType = "String")
    })
    @GetMapping("/query")
    public Result<Object> queryAllVideoMonitor(String keyword,String baseName,String regionName){
        Page<VideoMonitorModel> page =  getPage();

        return ResultUtil.success(videoMonitorService.queryAllVideoMonitors(page,keyword,baseName,regionName));
    }

    @SysLog(prefix = "新增视频监控",value = LogType.ALL)
    @ApiOperation("新增视频监控")
    @ApiImplicitParam(name = "videoMonitorDTO",value = "新增视频监控实体(主键自增）",paramType = "body",dataType = "VideoMonitorDTO")
    @PostMapping("/insert")
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> insertVideoMonitor(@Valid @RequestBody VideoMonitorDTO videoMonitorDTO, BindingResult result){
        if(result.hasErrors()){
            return ResultUtil.paramError();
        }else{
            Object savePoint = null;
            boolean flag = false;
            try{
                savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
                flag = videoMonitorService.insertVideoMonitor(videoMonitorDTO);
            }catch (Exception e){
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                return ResultUtil.databaseError(e.toString());
            }
            return ResultUtil.success(flag);
        }
    }

    @SysLog(prefix = "修改视频监控",value = LogType.ALL)
    @ApiOperation("修改视频监控")
    @ApiImplicitParam(name = "videoMonitorDTO",value = "修改视频监控实体（需要给出主键）",paramType = "body",dataType = "VideoMonitorDTO")
    @PutMapping("/update")
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> updateVideoMonitor(@Valid @RequestBody VideoMonitorDTO videoMonitorDTO, BindingResult result){
        if(result.hasErrors()){
            return ResultUtil.paramError();
        }else{
            Object savePoint = null;
            boolean flag = false;
            try{
                savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
                flag = videoMonitorService.updateVideoMonitor(videoMonitorDTO);
            }catch (Exception e){
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                return ResultUtil.databaseError(e.toString());
            }
            return ResultUtil.success(flag);
        }
    }

    @SysLog(prefix = "根据主键删除视频监控",value = LogType.ALL)
    @ApiOperation("根据主键删除视频监控")
    @ApiImplicitParam(name = "id",value = "视频监控主键",required = true,paramType = "path",dataType = "String")
    @DeleteMapping("/delete/{id}")
    public Result<Object> deleteById(@PathVariable("id") String id){
        boolean flag = true;
        try{
            log.info("videoMonitorId:" + id);
            flag = videoMonitorService.removeById(id);
        }catch (Exception e){
            log.error(e.toString());
            return ResultUtil.databaseError(flag);
        }
        if(flag == true){
            return ResultUtil.success(flag);
        }else{
            return ResultUtil.error("视频监控不存在");
        }
    }

    @SysLog(prefix = "批量删除视频监控",value = LogType.ALL)
    @ApiOperation("批量删除视频监控")
    @ApiImplicitParam(name = "ids[]",value = "视频监控主键数组",required = true,paramType = "query",allowMultiple = true,dataType = "String")
    @DeleteMapping("/deletes")
    public Result<Object> delete(@RequestParam("ids[]") String[] ids){
        boolean flag = true;
        try{
            log.info("videoMonitorIds.length:" + ids.length);
            flag = videoMonitorService.removeByIds(Arrays.asList(ids));
        }catch (Exception e){
            log.error(e.toString());
            return ResultUtil.databaseError(flag);
        }
        if(flag == true){
            return ResultUtil.success(flag);
        }else{
            return ResultUtil.error("视频监控不存在");
        }
    }

    @ApiOperation("导出视频监控模板")
    @GetMapping("/exportTemplateExcel")
    public void exportTemplateExcel(HttpServletResponse httpServletResponse){
        byte[] bytes = new byte[BUFFER_SIZE];
        try{

            httpServletResponse.setContentType("application/octet-stream");
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + "VideoMonitorTemplateExcel" + ".xls");

            String filePath = videoMonitorService.exportTemplateExcel();

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("templates/视频监控类导入模板.xls");

            int len;
            while((len = inputStream.read(bytes)) != -1){
                httpServletResponse.getOutputStream().write(bytes,0,len);
            }
            inputStream.close();
            httpServletResponse.getOutputStream().close();
            FileUtil.deleteFile(new File(new File(filePath).getParent()));
        }catch (Exception e){
            log.error(e.toString());
        }
    }

    @SysLog(prefix = "根据模板导入视频监控",value = LogType.ALL)
    @ApiOperation("根据模板导入视频监控")
    @PostMapping("/uploadFile")
    public Result<Object> uploadFile(@ApiParam MultipartFile multipartFile){
        boolean flag = true;
        try{
            flag = videoMonitorService.insertByTemplate(multipartFile);
        }catch (Exception e){
            log.error(e.toString());
            if(e instanceof PersistenceException){
                return ResultUtil.databaseError("视频监控名称重复！");
            }else{
                return ResultUtil.databaseError();
            }
        }
        if(flag == true){
            return ResultUtil.success(flag);
        }else{
            return ResultUtil.error();
        }
    }
}
