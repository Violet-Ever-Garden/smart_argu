package hzau.sa.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.dao.DiseaseInsectDao;
import hzau.sa.backstage.entity.DiseaseInsectDTO;
import hzau.sa.backstage.entity.DiseaseInsectIndexDTO;
import hzau.sa.backstage.entity.DiseaseInsectVO;
import hzau.sa.backstage.service.DiseaseInsectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.msg.dao.FileDao;
import hzau.sa.msg.entity.FileVO;
import hzau.sa.msg.enums.FileEnum;
import hzau.sa.msg.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DiseaseInsect服务实现类
 *
 *
 * @author lvhao
 * @since 2020-09-02
 */
@Slf4j
@Service
public class DiseaseInsectServiceImpl extends ServiceImpl<DiseaseInsectDao, DiseaseInsectVO> implements DiseaseInsectService {

    @Resource
    private DiseaseInsectDao diseaseInsectDao;

    @Resource
    private FileDao fileDao;

    @Override
    public boolean insertDiseaseInsect(DiseaseInsectVO diseaseInsectVO, MultipartFile[] files) throws IOException {
        try{
            diseaseInsectDao.insert(diseaseInsectVO);

            Integer diseaseInsectVOId = diseaseInsectVO.getDiseaseInsectId();

            FileEnum fileEnum = FileEnum.DISEASEINSECT;
            String fileMsg = diseaseInsectVO.getDiseaseInsectName() + diseaseInsectVO.getDiseaseInsectLabel();
            List<String> fileList = FileUtil.uploadFiles(fileEnum,fileMsg,files);

            for(String file : fileList){
                FileVO fileVO = new FileVO();
                fileVO.setFileType(fileEnum.name());
                fileVO.setConnectId(String.valueOf(diseaseInsectVOId));
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
    public List<DiseaseInsectIndexDTO> queryAllDiseaseInsect(Page page, String keyword, Integer wikiCropTypeId) {
        return diseaseInsectDao.queryAllDiseaseInsect(page,keyword,wikiCropTypeId);
    }

    @Override
    public DiseaseInsectDTO queryDiseaseInsectById(Integer diseaseInsectId) {
        try{
            DiseaseInsectDTO diseaseInsectDTO = diseaseInsectDao.queryDiseaseInsectById(diseaseInsectId);

            List<Map> pictureUrls = new ArrayList<>();
            for(FileVO fileVO : fileDao.selectList(new QueryWrapper<FileVO>()
                    .lambda()
                    .eq(FileVO::getConnectId,String.valueOf(diseaseInsectId))
                    .eq(FileVO::getFileType,FileEnum.DISEASEINSECT.name()))){
                Map pictureMsg = new HashMap();
                pictureMsg.put("pictureId",fileVO.getFileId());
                pictureMsg.put("pictureUrl",fileVO.getUrl());
                pictureUrls.add(pictureMsg);
            }

            diseaseInsectDTO.setPictureUrls(pictureUrls);

            return diseaseInsectDTO;
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }
    }

    @Override
    public boolean updateDiseaseInsect(DiseaseInsectVO diseaseInsectVO, MultipartFile[] files, String[] ids) throws IOException {
        try{
            diseaseInsectDao.updateById(diseaseInsectVO);

            for(String id : ids){
                FileUtil.deleteFile(fileDao.selectById(Integer.valueOf(id)).getFileAbsolutePath());
                fileDao.deleteById(id);
            }

            FileEnum fileEnum = FileEnum.DISEASEINSECT;
            String fileMsg = diseaseInsectVO.getDiseaseInsectName() + diseaseInsectVO.getDiseaseInsectLabel();
            List<String> fileList = FileUtil.uploadFiles(fileEnum,fileMsg,files);

            for(String file : fileList){
                FileVO fileVO = new FileVO();
                fileVO.setFileType(fileEnum.name());
                fileVO.setConnectId(String.valueOf(diseaseInsectVO.getDiseaseInsectId()));
                fileVO.setFileAbsolutePath(file);
                fileVO.setUrl(FileUtil.getFileUrl(file));
                fileDao.insert(fileVO);
            }

            return true;
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }
    }

    @Override
    public boolean deleteDiseaseInsectById(Integer diseaseInsectId) {
        try{
            List<FileVO> fileVOS = fileDao.selectList(new QueryWrapper<FileVO>()
                    .lambda()
                    .eq(FileVO::getConnectId,String.valueOf(diseaseInsectId))
                    .eq(FileVO::getFileType,FileEnum.DISEASEINSECT.name()));
            for(FileVO fileVO : fileVOS){
                FileUtil.deleteFile(fileVO.getFileAbsolutePath());
                fileDao.deleteById(fileVO.getFileId());
            }

            diseaseInsectDao.deleteById(diseaseInsectId);
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }
        return true;
    }

    @Override
    public boolean deleteDiseaseInsectByIds(String[] diseaseInsectIds) {
        try{
            for(String diseaseInsectId : diseaseInsectIds){
                List<FileVO> fileVOS = fileDao.selectList(new QueryWrapper<FileVO>()
                        .lambda()
                        .eq(FileVO::getConnectId,String.valueOf(diseaseInsectId))
                        .eq(FileVO::getFileType,FileEnum.DISEASEINSECT.name()));
                for(FileVO fileVO : fileVOS){
                    FileUtil.deleteFile(fileVO.getFileAbsolutePath());
                    fileDao.deleteById(fileVO.getFileId());
                }
                diseaseInsectDao.deleteById(diseaseInsectId);
            }
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }
        return true;
    }
}
