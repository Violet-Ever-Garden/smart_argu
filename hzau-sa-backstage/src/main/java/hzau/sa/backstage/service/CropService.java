package hzau.sa.backstage.service;

import hzau.sa.msg.entity.Result;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.backstage.dao.CropDao;
import hzau.sa.backstage.entity.CropVO;

/**
 * crop 服务实现类
 * @author haokai
 * @date 2020-08-12
 */
public interface CropService  {


    /**
     * 新增作物
     * @param cropVO
     * @return
     */
    public Result insert(CropVO cropVO);


    /**
     * 按id删除作物
     * @param cropId
     * @return
     */
    public Result delete(int cropId);
}