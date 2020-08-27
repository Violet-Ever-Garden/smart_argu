package hzau.sa.backstage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.dao.SensorDao;
import hzau.sa.backstage.dao.WeatherStationDao;
import hzau.sa.backstage.entity.SenesorModel;
import hzau.sa.backstage.entity.WeatherStationModel;
import hzau.sa.backstage.entity.WeatherStationVO;
import hzau.sa.backstage.service.WeatherStationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 气象站 服务实现类
 * @author haokai
 * @date 2020-08-26
 */
@Service
public class WeatherStationServiceImpl extends ServiceImpl<WeatherStationDao, WeatherStationVO> implements WeatherStationService {
    @Autowired
    WeatherStationDao weatherStationDao;
    public IPage<WeatherStationModel> selectSensorModel(Page<WeatherStationModel> page, String baseName, String sensorName){
        return  weatherStationDao.selectWeatherStationModel(page,baseName,sensorName);
    }
}
