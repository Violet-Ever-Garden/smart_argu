package hzau.sa.backstage.service;


import hzau.sa.msg.entity.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * crop 服务实现类
 * @author haokai
 * @date 2020-08-12
 */
public interface CropService  {



    public Result insert(String cropName , MultipartFile picture) throws IOException;


    /**
     * 按id删除作物
     * @param cropId
     * @return
     */
    public Result delete(int cropId);
}