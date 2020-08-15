package hzau.sa.backstage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.CropParameterModel;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.backstage.dao.CropParameterDao;
import hzau.sa.backstage.entity.CropParameterVO;

import java.util.List;

/**
 * cropParameter 服务实现类
 * @author haokai
 * @date 2020-08-12
 */
public interface CropParameterService  {

    /**
     * 按作物id分页模糊查询参数
     * @param page
     * @param cropId
     * @param keyword
     * @return
     */
    public List<CropParameterModel> selectCropParameterListPage(Page<CropParameterModel> page , int cropId, String keyword);
}