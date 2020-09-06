//package hzau.sa.bigDataCenter.controller;
//
//import java.util.Arrays;
//import java.util.List;
//import javax.validation.Valid;
//
//import hzau.sa.bigDataCenter.service.impl.DownloadServiceImpl;
//import hzau.sa.msg.annotation.SysLog;
//import hzau.sa.msg.controller.BaseController;
//import hzau.sa.msg.entity.Result;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import hzau.sa.bigDataCenter.entity.DownloadVO;
//import hzau.sa.bigDataCenter.service.DownloadService;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiImplicitParam;
//import org.springframework.web.multipart.MultipartFile;
//
//
///**
// *  控制器
// * @author wuyihu
// * @date 2020-09-06
// */
//@Slf4j
//@RestController
//@RequestMapping("sys/download")
//@Api(value = "-API", tags = { "文件及文件夹上传相关接口" })
//public class DownloadController extends BaseController {
//
//    @Autowired
//    private DownloadServiceImpl downloadService;
//
//    /**
//     * 文件或文件夹上传
//     */
//    @SysLog(prefix = "文件或文件夹上传")
//    @ApiOperation("文件或文件夹上传")
//    @PostMapping("/folderUpload")
//    public Result folderUpload(@RequestParam(value = "folder",required = true) MultipartFile[] folder){
//       return downloadService.folderUpload(folder);
//    }
//}