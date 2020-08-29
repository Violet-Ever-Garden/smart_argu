package hzau.sa.trainingReport.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.msg.dao.FileDao;
import hzau.sa.msg.entity.FileVO;
import hzau.sa.msg.enums.FileEnum;
import hzau.sa.msg.util.FileUtil;
import hzau.sa.trainingReport.dao.TrainingReportAsTeacherclassDao;
import hzau.sa.trainingReport.dao.MeasuremanageDao;
import hzau.sa.trainingReport.dao.TrainingReportStudentDao;
import hzau.sa.trainingReport.entity.*;
import hzau.sa.trainingReport.service.MeasuremanageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 *
 * 措施管理服务实现类
 *
 * @author lvhao
 * @since 2020-08-18
 */
@Service
@Slf4j
public class MeasuremanageServiceImpl extends ServiceImpl<MeasuremanageDao, MeasuremanageVO> implements MeasuremanageService {

    @Resource
    private MeasuremanageDao measuremanageDao;

    @Resource
    private FileDao fileDao;

    @Resource
    private TrainingReportAsTeacherclassDao trainingReportAsTeacherclassDao;

    @Resource
    private TrainingReportStudentDao trainingReportStudentDao;


    @Override
    public boolean insertMeasure(MeasureManageRequest measureManageRequest) throws IOException {
        try{
            MeasuremanageVO measuremanageVO = new MeasuremanageVO();
            measuremanageVO.setStudentId(measureManageRequest.getStudentId());
            measuremanageVO.setMeasureId(measuremanageDao.queryMeasureIdByMeasureName(measureManageRequest.getMeasure()));
            measuremanageVO.setCropId(measuremanageDao.queryCropIdByCropName(measureManageRequest.getCrop()));
            measuremanageVO.setMeasureContent(measureManageRequest.getMeasureContent());
            measuremanageVO.setCreateTime(measureManageRequest.getCreateTime());
            measuremanageDao.insert(measuremanageVO);

            Integer measureManegeId = measuremanageVO.getMeasuremanageId();
            FileEnum fileEnum = FileEnum.MEASURE;
            String fileMsg = measureManageRequest.getStudentId() + measureManageRequest.getCrop() + measureManageRequest.getMeasure();
            List<String> files = FileUtil.uploadFiles(fileEnum,fileMsg,measureManageRequest.getMultipartFiles());

            for(String file : files){
                FileVO fileVO = new FileVO();
                fileVO.setFileType(fileEnum.name());
                fileVO.setConnectId(String.valueOf(measureManegeId));
                fileVO.setFileAbsolutePath(file);
                fileVO.setUrl(FileUtil.getFileUrl(file));
                fileDao.insert(fileVO);
            }
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }
        return true;
    }

    @Override
    public List<MeasureManageResponse> queryMeasure(String studentId,String cropName) {
        List<MeasureManageResponse> measureManageResponses = new ArrayList<>();
        try{
            List<MeasuremanageVO> measuremanageVOList = measuremanageDao.selectList(new QueryWrapper<MeasuremanageVO>()
                    .lambda()
                    .eq(MeasuremanageVO::getStudentId,studentId)
                    .eq(MeasuremanageVO::getCropId,measuremanageDao.queryCropIdByCropName(cropName)));

            for(MeasuremanageVO measuremanageVO : measuremanageVOList){
                MeasureManageResponse measureManageResponse = new MeasureManageResponse();
                measureManageResponse.setMeasureManageId(measuremanageVO.getMeasuremanageId());
                measureManageResponse.setMeasureContent(measuremanageVO.getMeasureContent());
                measureManageResponse.setMeasureName(measuremanageDao.queryMeasureNameById(measuremanageVO.getMeasureId()));
                measureManageResponse.setMeasureTime(measuremanageVO.getCreateTime());
                List<Map> pictureUrls = new ArrayList<>();
                for(FileVO fileVO : fileDao.selectList(new QueryWrapper<FileVO>()
                        .lambda()
                        .eq(FileVO::getConnectId,String.valueOf(measuremanageVO.getMeasuremanageId()))
                        .eq(FileVO::getFileType,FileEnum.MEASURE.name()))){
                    Map pictureMsg = new HashMap();
                    pictureMsg.put("pictureId",fileVO.getFileId());
                    pictureMsg.put("pictureUrl",fileVO.getUrl());
                    pictureUrls.add(pictureMsg);
                }
                measureManageResponse.setPictureUrls(pictureUrls);

                measureManageResponses.add(measureManageResponse);
            }
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }

        return measureManageResponses;
    }

    @Override
    public boolean updateMeasure(MeasureManageRequest measureManageRequest,Integer measureManageId, String[] ids) throws IOException {
        try{
            MeasuremanageVO measuremanageVO = new MeasuremanageVO();
            measuremanageVO.setMeasuremanageId(measureManageId);
            measuremanageVO.setMeasureId(measuremanageDao.queryMeasureIdByMeasureName(measureManageRequest.getMeasure()));
            measuremanageVO.setMeasureContent(measureManageRequest.getMeasureContent());
            measuremanageVO.setCreateTime(measureManageRequest.getCreateTime());
            measuremanageDao.updateById(measuremanageVO);

            for(String id : ids){
                FileUtil.deleteFile(fileDao.selectById(Integer.valueOf(id)).getFileAbsolutePath());
                fileDao.deleteById(id);
            }

            FileEnum fileEnum = FileEnum.MEASURE;
            measuremanageVO = measuremanageDao.selectById(measureManageId);
            String fileMsg = measuremanageVO.getStudentId() + measuremanageDao.queryCropNameByCropId(measuremanageVO.getCropId()) + measuremanageDao.queryMeasureNameById(measuremanageVO.getMeasureId());
            List<String> files = FileUtil.uploadFiles(fileEnum,fileMsg,measureManageRequest.getMultipartFiles());

            for(String file : files){
                FileVO fileVO = new FileVO();
                fileVO.setFileType(fileEnum.name());
                fileVO.setConnectId(String.valueOf(measureManageId));
                fileVO.setFileAbsolutePath(file);
                fileVO.setUrl(FileUtil.getFileUrl(file));
                fileDao.insert(fileVO);
            }
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }
        return true;
    }

    @Override
    public boolean deleteMeasure(Integer measureManageId) {
        try{
            List<FileVO> fileVOS = fileDao.selectList(new QueryWrapper<FileVO>()
                    .lambda()
                    .eq(FileVO::getConnectId,String.valueOf(measureManageId))
                    .eq(FileVO::getFileType,FileEnum.MEASURE.name()));
            for(FileVO fileVO : fileVOS){
                FileUtil.deleteFile(fileVO.getFileAbsolutePath());
                fileDao.deleteById(fileVO.getFileId());
            }

            measuremanageDao.deleteById(measureManageId);
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }
        return true;
    }

    @Override
    public Integer queryGradeIdByName(String gradeName) {
        return measuremanageDao.queryGradeIdByName(gradeName);
    }

    @Override
    public List<Integer> queryClassIdByName(String className) {
        return measuremanageDao.queryClassIdByName(className);
    }

    @Override
    public List<String> queryStudentIdByName(String studentName) {
        return measuremanageDao.queryStudentIdByName(studentName);
    }

    @Override
    public Map queryClassByTeacherId(Page page, QueryWrapper queryWrapper) {

        Map resultMap = new HashMap<>();

        IPage pageInfo = trainingReportAsTeacherclassDao.selectPage(page,queryWrapper);

        List<ClassOfTeacher> classOfTeacherList = new ArrayList<>();

        List<AsTeacherclassVO> asTeacherclassVOList = pageInfo.getRecords();

        for(AsTeacherclassVO asTeacherclassVO : asTeacherclassVOList){
            ClassOfTeacher classOfTeacher = new ClassOfTeacher();
            classOfTeacher.setClassId(asTeacherclassVO.getClassId());
            classOfTeacher.setClassName(measuremanageDao.queryClassNameById(asTeacherclassVO.getClassId()));
            classOfTeacher.setClassGrade(measuremanageDao.queryGradeNameById(asTeacherclassVO.getGradeId()));
            classOfTeacher.setClassField(measuremanageDao.queryClassFieldByClassId(asTeacherclassVO.getClassId()));
            classOfTeacherList.add(classOfTeacher);
        }

        resultMap.put("total",pageInfo.getTotal());
        resultMap.put("rows",classOfTeacherList);

        return resultMap;
    }

    @Override
    public Map queryStudentByClassId(Page page, QueryWrapper queryWrapper) {

        Map resultMap = new HashMap();

        IPage pageInfo = trainingReportStudentDao.selectPage(page,queryWrapper);

        List<StudentVO> studentVOList = pageInfo.getRecords();

        List<String> studentIds = new ArrayList<>();
        for(StudentVO studentVO : studentVOList){
            studentIds.add(studentVO.getStudentId());
        }
        if(studentIds.isEmpty()){
            resultMap.put("total",pageInfo.getTotal());
            resultMap.put("rows",pageInfo.getRecords());
            return resultMap;
        }

        List<StudentOfClass> studentOfClasses = measuremanageDao.queryStudentByStudentId(studentIds);

        resultMap.put("total",pageInfo.getTotal());
        resultMap.put("rows",studentOfClasses);

        return resultMap;
    }
}
