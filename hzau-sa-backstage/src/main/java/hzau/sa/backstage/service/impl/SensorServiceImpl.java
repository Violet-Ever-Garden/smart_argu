package hzau.sa.backstage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.dao.SensorDao;
import hzau.sa.backstage.entity.SenesorModel;
import hzau.sa.backstage.entity.SensorVO;
import hzau.sa.backstage.service.SensorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lvhao
 * @since 2020-08-25
 */
@Service
public class SensorServiceImpl extends ServiceImpl<SensorDao, SensorVO> implements SensorService {
    @Autowired
    SensorDao sensorDao;
    public IPage<SenesorModel>  selectSensorModel(Page<SenesorModel> page, String baseName, String sensorName){
        return  sensorDao.selectSensorModel(page,baseName,sensorName);
    }
}
