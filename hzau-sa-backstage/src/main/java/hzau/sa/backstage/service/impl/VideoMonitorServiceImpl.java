package hzau.sa.backstage.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.dao.VideoMonitorDao;
import hzau.sa.backstage.entity.VideoMonitorDTO;
import hzau.sa.backstage.entity.VideoMonitorModel;
import hzau.sa.backstage.entity.VideoMonitorVO;
import hzau.sa.backstage.listener.VideoMonitorListener;
import hzau.sa.backstage.service.VideoMonitorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.backstage.template.VideoMonitorTemplate;
import hzau.sa.msg.excel.MyExcelUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *  服务实现类
 *
 * @author lvhao
 * @since 2020-08-27
 */
@Slf4j
@Service
public class VideoMonitorServiceImpl extends ServiceImpl<VideoMonitorDao, VideoMonitorVO> implements VideoMonitorService {

    @Value("${file.excel}")
    private String excelFilePath;

    @Resource
    private VideoMonitorDao videoMonitorDao;

    @Override
    public List<VideoMonitorModel> queryAllVideoMonitors(Page page, String keyWord, String baseName, String regionName) {
        return videoMonitorDao.queryAllVideoMonitor(page,keyWord,baseName,regionName);
    }

    @Override
    public boolean insertVideoMonitor(VideoMonitorDTO videoMonitorDTO) {
        VideoMonitorVO videoMonitorVO = new VideoMonitorVO();
        videoMonitorVO.setBaseId(videoMonitorDao.queryBaseIdByName(videoMonitorDTO.getBaseName()));
        videoMonitorVO.setRegionId(videoMonitorDao.queryRegionIdByName(videoMonitorDTO.getRegionName()));
        videoMonitorVO.setVideoMonitorDeviceName(videoMonitorDTO.getVideoMonitorDeviceName());
        videoMonitorVO.setDeviceNumber(videoMonitorDTO.getDeviceNumber());
        videoMonitorVO.setDeviceIp(videoMonitorDTO.getDeviceIp());
        videoMonitorVO.setDevicePort(videoMonitorDTO.getDevicePort());
        videoMonitorVO.setDeviceAccountNumber(videoMonitorDTO.getDeviceAccountNumber());
        videoMonitorVO.setDevicePassword(videoMonitorDTO.getDevicePassword());
        videoMonitorVO.setVideoChannel(videoMonitorDTO.getVideoChannel());
        videoMonitorVO.setHomeShow(videoMonitorDTO.getHomeShow());
        videoMonitorVO.setVideoApplication(videoMonitorDTO.getVideoApplication());
        videoMonitorVO.setIsYingshiyun(videoMonitorDTO.getIsYingshiyun());
        videoMonitorVO.setAppKey(videoMonitorDTO.getAppKey());
        videoMonitorVO.setSecret(videoMonitorDTO.getSecret());
        videoMonitorVO.setRtmpPlayAddress(videoMonitorDTO.getRtmpPlayAddress());

        try{
            videoMonitorDao.insert(videoMonitorVO);
            return true;
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }
    }

    @Override
    public boolean updateVideoMonitor(VideoMonitorDTO videoMonitorDTO) {
        VideoMonitorVO videoMonitorVO = new VideoMonitorVO();
        videoMonitorVO.setVideoMonitorId(videoMonitorDTO.getVideoMonitorId());
        videoMonitorVO.setBaseId(videoMonitorDao.queryBaseIdByName(videoMonitorDTO.getBaseName()));
        videoMonitorVO.setRegionId(videoMonitorDao.queryRegionIdByName(videoMonitorDTO.getRegionName()));
        videoMonitorVO.setVideoMonitorDeviceName(videoMonitorDTO.getVideoMonitorDeviceName());
        videoMonitorVO.setDeviceNumber(videoMonitorDTO.getDeviceNumber());
        videoMonitorVO.setDeviceIp(videoMonitorDTO.getDeviceIp());
        videoMonitorVO.setDevicePort(videoMonitorDTO.getDevicePort());
        videoMonitorVO.setDeviceAccountNumber(videoMonitorDTO.getDeviceAccountNumber());
        videoMonitorVO.setDevicePassword(videoMonitorDTO.getDevicePassword());
        videoMonitorVO.setVideoChannel(videoMonitorDTO.getVideoChannel());
        videoMonitorVO.setHomeShow(videoMonitorDTO.getHomeShow());
        videoMonitorVO.setVideoApplication(videoMonitorDTO.getVideoApplication());
        videoMonitorVO.setIsYingshiyun(videoMonitorDTO.getIsYingshiyun());
        videoMonitorVO.setAppKey(videoMonitorDTO.getAppKey());
        videoMonitorVO.setSecret(videoMonitorDTO.getSecret());
        videoMonitorVO.setRtmpPlayAddress(videoMonitorDTO.getRtmpPlayAddress());

        try{
            videoMonitorDao.updateById(videoMonitorVO);
            return true;
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }
    }

    @Override
    public String exportTemplateExcel() {

        File file = new File(excelFilePath);
        if(!file.exists()){
            file.mkdirs();
        }

        String fileName = "视频监控导入模板";
        try{
            MyExcelUtil.generateExcel(excelFilePath,fileName, VideoMonitorTemplate.class,new ArrayList());
            return excelFilePath + File.separator + fileName + ".xls";
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }
    }

    @Override
    public boolean insertByTemplate(MultipartFile multipartFile) throws IOException {
        try{
            VideoMonitorListener videoMonitorListener = new VideoMonitorListener(videoMonitorDao, this);
            EasyExcel.read(multipartFile.getInputStream(),VideoMonitorTemplate.class,videoMonitorListener).sheet().doRead();
            return true;
        }catch (Exception e){
            log.error(e.toString());
            throw  e;
        }
    }
}
