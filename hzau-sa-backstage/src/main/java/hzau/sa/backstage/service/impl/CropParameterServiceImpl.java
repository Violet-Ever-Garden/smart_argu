package hzau.sa.backstage.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.dao.CropParameterDao;
import hzau.sa.backstage.entity.CropParameterModel;
import hzau.sa.backstage.entity.CropParameterVO;
import hzau.sa.backstage.service.CropParameterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author haokai
 * @since 2020-08-12
 */
@Service
public class CropParameterServiceImpl extends ServiceImpl<CropParameterDao, CropParameterVO> implements CropParameterService {
    @Autowired
    CropParameterDao cropParameterDao;

    public List<CropParameterModel> selectCropParameterListPage(Page<CropParameterModel> page , int cropId, String keyword){
        List<CropParameterModel> cropParameterModels = cropParameterDao.selectCropParameterListPage(page , cropId,"%"+keyword+"%");
        return cropParameterModels;
    }
}
