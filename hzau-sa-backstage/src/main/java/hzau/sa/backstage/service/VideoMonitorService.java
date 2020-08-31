package hzau.sa.backstage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import hzau.sa.backstage.entity.VideoMonitorDTO;
import hzau.sa.backstage.entity.VideoMonitorModel;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.backstage.dao.VideoMonitorDao;
import hzau.sa.backstage.entity.VideoMonitorVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * videoMonitor 服务实现类
 * @author lvhao
 * @date 2020-08-27
 */
public interface VideoMonitorService  extends IService<VideoMonitorVO> {

    /**
     * 分页查询所有的视频监控
     * @param page 分页
     * @param keyWord 名称和编号关键字
     * @param baseName 基地名称
     * @param regionName 区域名称
     * @return
     */
    public List<VideoMonitorModel> queryAllVideoMonitors(Page page,String keyWord,String baseName, String regionName);

    /**
     * 新增视频监控
     * @param videoMonitorDTO
     * @return
     */
    public boolean insertVideoMonitor(VideoMonitorDTO videoMonitorDTO);

    /**
     * 根据主键修改视频监控
     * @param videoMonitorDTO 实体
     * @return
     */
    public boolean updateVideoMonitor(VideoMonitorDTO videoMonitorDTO);

    /**
     * 导出视频监控模板
     * @return
     */
    public String exportTemplateExcel();

    /**
     * 根据模板插入数据
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public boolean insertByTemplate(MultipartFile multipartFile) throws IOException;

}