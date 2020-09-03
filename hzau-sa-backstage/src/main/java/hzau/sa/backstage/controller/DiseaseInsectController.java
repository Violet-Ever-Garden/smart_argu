package hzau.sa.backstage.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.DiseaseInsectIndexDTO;
import hzau.sa.backstage.entity.DiseaseInsectVO;
import hzau.sa.backstage.entity.WikiCropTypeDTO;
import hzau.sa.backstage.service.DiseaseInsectService;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.controller.BaseController;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.LogType;
import hzau.sa.msg.util.ResultUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author LvHao
 * @Description :
 * @date 2020-09-02 20:45
 */
@Slf4j
@RestController
@RequestMapping("/diseaseInsect")
@Api(value = "病虫害百科-API接口",tags = "病虫害百科相关接口")
public class DiseaseInsectController extends BaseController {

    @Resource
    private DiseaseInsectService diseaseInsectService;

    @ApiOperation("病虫害百科查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数（默认1 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "limit",value = "容量（默认20 可为null）",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "keyword",value = "关键字",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "wikiCropTypeId",value = "百科植物类型ID",paramType = "query",dataType = "String")
    })
    @GetMapping("/query")
    public Result<Object> queryAllDiseaseInsect(String keyword,String wikiCropTypeId){
        Page<DiseaseInsectIndexDTO> page =  getPage();

        if(StrUtil.isNotBlank(wikiCropTypeId)){
            return ResultUtil.success(diseaseInsectService.queryAllDiseaseInsect(page,keyword, Integer.valueOf(wikiCropTypeId)));
        }

        return ResultUtil.success(diseaseInsectService.queryAllDiseaseInsect(page,keyword,null));
    }

    @ApiOperation("按id查询病虫害百科")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diseaseInsectId",value = "需要查询病虫害百科ID",required = true,paramType = "path",dataType = "String")
    })
    @GetMapping("/queryById/{diseaseInsectId}")
    public Result<Object> queryById(@PathVariable("diseaseInsectId") String diseaseInsectId){
        try{
            return ResultUtil.success(diseaseInsectService.queryDiseaseInsectById(Integer.valueOf(diseaseInsectId)));
        }catch (Exception e){
            return ResultUtil.error(e.toString());
        }
    }

    @SysLog(prefix = "新增病虫害百科",value = LogType.ALL)
    @ApiOperation("新增病虫害百科")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diseaseInsectName",value = "病虫害名称",required = true,paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "diseaseInsectAlias",value = "病虫害别名",paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "diseaseInsectLabel",value = "病虫害标示",required = true,paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "wikiCategoryId",value = "百科Id",required = true,paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "wikiCropTypeId",value = "百科植物Id",required = true,paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "diseaseInsectIntroduction",value = "病虫害简介",paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "plantSymptom",value = "植物症状",paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "morphologyFeature",value = "形态特征",paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "livingHabit",value = "生活习性",paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "propagationPath",value = "传播途径",paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "occurrenceRegularity",value = "发生规律",paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "agricultureControl",value = "农业防治",paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "chemistryControl",value = "化学防治",paramType = "form",dataType = "String")
    })
    @PostMapping("/insert")
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> insertDiseaseInsect(String diseaseInsectName,
                                              String diseaseInsectAlias,
                                              String diseaseInsectLabel,
                                              String wikiCategoryId,
                                              String wikiCropTypeId,
                                              String diseaseInsectIntroduction,
                                              String plantSymptom,
                                              String morphologyFeature,
                                              String livingHabit,
                                              String propagationPath,
                                              String occurrenceRegularity,
                                              String agricultureControl,
                                              String chemistryControl,
                                              @ApiParam(name = "files",value = "图片数组") MultipartFile[] files){
        Object savePoint = null;
        boolean flag = false;
        try{
            savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();

            DiseaseInsectVO diseaseInsectVO = new DiseaseInsectVO();
            diseaseInsectVO.setDiseaseInsectName(diseaseInsectName);
            diseaseInsectVO.setDiseaseInsectLabel(diseaseInsectLabel);
            diseaseInsectVO.setDiseaseInsectAlias(diseaseInsectAlias);
            diseaseInsectVO.setWikiCropTypeId(Integer.valueOf(wikiCropTypeId));
            diseaseInsectVO.setWikiCategoryId(Integer.valueOf(wikiCategoryId));
            diseaseInsectVO.setDiseaseInsectIntroduction(diseaseInsectIntroduction);
            diseaseInsectVO.setPlantSymptom(plantSymptom);
            diseaseInsectVO.setMorphologyFeature(morphologyFeature);
            diseaseInsectVO.setLivingHabit(livingHabit);
            diseaseInsectVO.setPropagationPath(propagationPath);
            diseaseInsectVO.setOccurrenceRegularity(occurrenceRegularity);
            diseaseInsectVO.setAgricultureControl(agricultureControl);
            diseaseInsectVO.setChemistryControl(chemistryControl);

            log.info("diseaseInsectVO:" + diseaseInsectVO.toString() + "  files:" + files);
            flag = diseaseInsectService.insertDiseaseInsect(diseaseInsectVO,files);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            return ResultUtil.databaseError(e.toString());
        }

        return ResultUtil.success(flag);
    }

    @SysLog(prefix = "修改病虫害百科",value = LogType.ALL)
    @ApiOperation("修改病虫害百科")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "diseaseInsectId",value = "需要修改id",required = true,paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "diseaseInsectName",value = "病虫害名称",required = true,paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "diseaseInsectAlias",value = "病虫害别名",paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "diseaseInsectLabel",value = "病虫害标示",required = true,paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "wikiCategoryId",value = "百科Id",required = true,paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "wikiCropTypeId",value = "百科植物Id",required = true,paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "diseaseInsectIntroduction",value = "病虫害简介",paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "plantSymptom",value = "植物症状",paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "morphologyFeature",value = "形态特征",paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "livingHabit",value = "生活习性",paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "propagationPath",value = "传播途径",paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "occurrenceRegularity",value = "发生规律",paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "agricultureControl",value = "农业防治",paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "chemistryControl",value = "化学防治",paramType = "form",dataType = "String"),
            @ApiImplicitParam(name = "ids",value = "需要删除图片ID数组",required = true,paramType = "form",allowMultiple = true,dataType = "String")
    })
    @PutMapping("/update")
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> updateDiseaseInsect(String diseaseInsectId,
                                              String diseaseInsectName,
                                              String diseaseInsectAlias,
                                              String diseaseInsectLabel,
                                              String wikiCategoryId,
                                              String wikiCropTypeId,
                                              String diseaseInsectIntroduction,
                                              String plantSymptom,
                                              String morphologyFeature,
                                              String livingHabit,
                                              String propagationPath,
                                              String occurrenceRegularity,
                                              String agricultureControl,
                                              String chemistryControl,
                                              @RequestParam("ids") String[] ids,
                                              @ApiParam(name = "files",value = "图片数组") MultipartFile[] files){
        Object savePoint = null;
        boolean flag = false;
        try{
            savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();

            DiseaseInsectVO diseaseInsectVO = new DiseaseInsectVO();
            diseaseInsectVO.setDiseaseInsectId(Integer.valueOf(diseaseInsectId));
            diseaseInsectVO.setDiseaseInsectName(diseaseInsectName);
            diseaseInsectVO.setDiseaseInsectLabel(diseaseInsectLabel);
            diseaseInsectVO.setDiseaseInsectAlias(diseaseInsectAlias);
            diseaseInsectVO.setWikiCropTypeId(Integer.valueOf(wikiCropTypeId));
            diseaseInsectVO.setWikiCategoryId(Integer.valueOf(wikiCategoryId));
            diseaseInsectVO.setDiseaseInsectIntroduction(diseaseInsectIntroduction);
            diseaseInsectVO.setPlantSymptom(plantSymptom);
            diseaseInsectVO.setMorphologyFeature(morphologyFeature);
            diseaseInsectVO.setLivingHabit(livingHabit);
            diseaseInsectVO.setPropagationPath(propagationPath);
            diseaseInsectVO.setOccurrenceRegularity(occurrenceRegularity);
            diseaseInsectVO.setAgricultureControl(agricultureControl);
            diseaseInsectVO.setChemistryControl(chemistryControl);

            log.info("diseaseInsectVO:" + diseaseInsectVO.toString() + "  files:" + files + " ids:" + ids);
            flag = diseaseInsectService.updateDiseaseInsect(diseaseInsectVO,files,ids);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            return ResultUtil.databaseError(e.toString());
        }

        return ResultUtil.success(flag);
    }

    @SysLog(prefix = "根据主键删除病虫害百科",value = LogType.ALL)
    @ApiOperation("根据主键删除病虫害百科")
    @ApiImplicitParam(name = "id",value = "病虫害百科主键",required = true,paramType = "path",dataType = "String")
    @DeleteMapping("/delete/{id}")
    public Result<Object> deleteById(@PathVariable("id") String id){
        boolean flag = true;
        try{
            log.info("diseaseInsectId:" + id);
            flag = diseaseInsectService.deleteDiseaseInsectById(Integer.valueOf(id));
        }catch (Exception e){
            log.error(e.toString());
            return ResultUtil.databaseError(flag);
        }
        if(flag == true){
            return ResultUtil.success(flag);
        }else{
            return ResultUtil.error("病虫害百科不存在");
        }
    }

    @SysLog(prefix = "批量删除病虫害百科",value = LogType.ALL)
    @ApiOperation("批量删除病虫害百科")
    @ApiImplicitParam(name = "ids[]",value = "病虫害百科主键数组",required = true,paramType = "query",allowMultiple = true,dataType = "String")
    @DeleteMapping("/deletes")
    public Result<Object> delete(@RequestParam("ids[]") String[] ids){
        boolean flag = true;
        try{
            log.info("diseaseInsectIds.length:" + ids.length);
            flag = diseaseInsectService.deleteDiseaseInsectByIds(ids);
        }catch (Exception e){
            log.error(e.toString());
            return ResultUtil.databaseError(flag);
        }
        if(flag == true){
            return ResultUtil.success(flag);
        }else{
            return ResultUtil.error("病虫害百科不存在");
        }
    }

}
