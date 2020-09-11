package hzau.sa.bigDataCenter.service;

import hzau.sa.msg.entity.Result;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.bigDataCenter.dao.DownloadDao;
import hzau.sa.bigDataCenter.entity.DownloadVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * download 服务实现类
 * @author lvhao
 * @date 2020-09-06
 */
public interface DownloadService  {
    public Result uploadFolder(String fileName,String fileIntroduction,MultipartFile[] files);
    public Result deleteFiles(Integer[] fileIds);
    public Result page();
    public Result downloadFile(Integer fileId);
}