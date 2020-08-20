package hzau.sa.file.controller;

import hzau.sa.msg.util.ZipUtil;
import hzau.sa.msg.enums.FileEnum;
import hzau.sa.msg.util.FileUtil;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LvHao
 * @Description :
 * @date 2020-08-14 15:35
 */
@RestController
@Slf4j
@Api("文件传输测试API")
public class FileTestController {

    private String path = "C:\\Users\\LvHao\\Desktop\\物联网项目\\file\\";
    private String DIR = "test\\";
    private String FILE_DIR = "C:\\Users\\LvHao\\Desktop\\物联网项目\\zip";


    @ApiOperation("上传文件")
    @PostMapping("/file")
    public Result<Object> fileTest(@ApiParam(name = "files",value = "文件") MultipartFile files){
        try{
            return ResultUtil.success(FileUtil.uploadFile(FileEnum.REPORT,"测试",files));
        }catch (Exception e){
            return ResultUtil.error(e.toString());
        }
    }

    @ApiOperation("上传多个文件")
    @PostMapping("/files")
    public Result<Object> filesTest(@ApiParam(name = "files",value = "文件") MultipartFile[] files){
        try{
            return ResultUtil.success(FileUtil.uploadFiles(FileEnum.REPORT,"测试",files));
        }catch (Exception e){
            return ResultUtil.error(e.toString());
        }
    }

    @ApiOperation("删除文件")
    @ApiImplicitParam(name = "filePath",value = "文件路径",required = true,paramType = "query",dataType = "String")
    @DeleteMapping("/delete")
    public Result<Object> fileTestDelete(String filePath){
        try{
            return ResultUtil.success(FileUtil.deleteFile(filePath));
        }catch (Exception e){
            return ResultUtil.error();
        }
    }

    @ApiOperation("修改文件")
    @ApiImplicitParam(name = "filePath",value = "文件路径",required = true,paramType = "query",dataType = "String")
    @PutMapping("/changeFile")
    public Result<Object> fileChange(@ApiParam(name = "files",value = "文件") MultipartFile files,String filePath){
        try{
            return ResultUtil.success(FileUtil.changeFile(filePath,files));
        }catch (Exception e){
            return ResultUtil.error(e.toString());
        }
    }

    @ApiOperation("获取文件URL")
    @ApiImplicitParam(name = "filePath",value = "文件路径",required = true,paramType = "query",dataType = "String")
    @GetMapping("/url")
    public Result<Object> fileURL(String filePath){
        try{
            return ResultUtil.success(FileUtil.getFileUrl(filePath));
        }catch (Exception e){
            return ResultUtil.error();
        }
    }

    @ApiOperation("获取zip压缩包")
    @GetMapping("/zip")
    public void getZip(HttpServletResponse httpServletResponse) throws UnsupportedEncodingException {
        String fileDir = FILE_DIR;

        if(new File(FILE_DIR).exists()){

            httpServletResponse.setContentType("application/octet-stream");
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-report" + ".zip");

            try{
                ZipUtil.zipCompress(fileDir,httpServletResponse.getOutputStream());
            }catch (Exception e){
                log.error(e.toString());
            }
        }
    }
}
