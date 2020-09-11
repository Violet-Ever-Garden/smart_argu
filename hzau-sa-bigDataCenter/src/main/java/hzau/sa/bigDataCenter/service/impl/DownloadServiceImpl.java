package hzau.sa.bigDataCenter.service.impl;

import hzau.sa.bigDataCenter.dao.DownloadDao;
import hzau.sa.bigDataCenter.entity.DownloadVO;
import hzau.sa.bigDataCenter.service.DownloadService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.FileUtil;
import hzau.sa.msg.util.ResultUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lvhao
 * @since 2020-09-06
 */
@Service
public class DownloadServiceImpl extends ServiceImpl<DownloadDao, DownloadVO> implements DownloadService {
    /**
     * 文件或者文件夹上传
     * @param fileName
     * @param fileIntroduction
     * @param files
     * @return
     */
    @Override
    public Result uploadFolder(String fileName, String fileIntroduction, MultipartFile[] files){
        return ResultUtil.success();
    }

    /**
     * 文件删除
     * @param fileIds
     * @return
     */
    @Override
    public Result deleteFiles(Integer[] fileIds){
        return ResultUtil.success();
    }

    /**
     * 分页
     * @return
     */
    @Override
    public Result page(){
        return ResultUtil.success();
    }

    /**
     * 文件下载
     * @param fileId
     * @return
     */
    @Override
    public Result downloadFile(Integer fileId){
        return ResultUtil.success();
    }
}
