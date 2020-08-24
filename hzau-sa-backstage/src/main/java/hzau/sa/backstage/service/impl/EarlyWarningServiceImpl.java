package hzau.sa.backstage.service.impl;

import hzau.sa.backstage.dao.EarlyWarningDao;
import hzau.sa.backstage.entity.EarlyWarningModel;
import hzau.sa.backstage.entity.EarlyWarningVO;
import hzau.sa.backstage.service.EarlyWarningService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author haokai
 * @since 2020-08-23
 */
@Service
public class EarlyWarningServiceImpl extends ServiceImpl<EarlyWarningDao, EarlyWarningVO> implements EarlyWarningService {
    @Autowired
    EarlyWarningDao earlyWarningDao;
    public List<EarlyWarningModel> selectEarlyWarningModel(){
        return earlyWarningDao.selectEarlyWarningModel();
    }
}
