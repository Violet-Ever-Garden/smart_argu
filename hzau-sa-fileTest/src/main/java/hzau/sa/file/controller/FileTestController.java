package hzau.sa.file.controller;

import hzau.sa.file.enums.FileEnum;
import hzau.sa.file.util.FileUtil;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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


    @ApiOperation("上传文件")
    @PostMapping("/file")
    public Result<Object> fileTest(@ApiParam(name = "files",value = "文件") MultipartFile files){
        try{
            return ResultUtil.success(FileUtil.uploadFile(FileEnum.REPORT,"测试",files));
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
}
