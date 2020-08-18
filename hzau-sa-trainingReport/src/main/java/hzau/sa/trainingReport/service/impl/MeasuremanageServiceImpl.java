package hzau.sa.trainingReport.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import hzau.sa.msg.dao.FileDao;
import hzau.sa.msg.entity.FileVO;
import hzau.sa.msg.enums.FileEnum;
import hzau.sa.msg.util.FileUtil;
import hzau.sa.trainingReport.dao.MeasuremanageDao;
import hzau.sa.trainingReport.entity.MeasureManageRequest;
import hzau.sa.trainingReport.entity.MeasureManageResponse;
import hzau.sa.trainingReport.entity.MeasuremanageVO;
import hzau.sa.trainingReport.service.MeasuremanageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                measureManageResponse.setMeasureTime(String.valueOf(measuremanageVO.getCreateTime()));
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
}
