package hzau.sa.backstage.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.dao.SensorDao;
import hzau.sa.backstage.dao.WeatherStationDao;
import hzau.sa.backstage.entity.SensorWrapper;
import hzau.sa.backstage.entity.WeatherStationModel;
import hzau.sa.backstage.entity.WeatherStationVO;
import hzau.sa.backstage.listener.SensorListener;
import hzau.sa.backstage.listener.WeatherStationListener;
import hzau.sa.backstage.service.WeatherStationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 气象站 服务实现类
 * @author haokai
 * @date 2020-08-26
 */
@Service
public class WeatherStationServiceImpl extends ServiceImpl<WeatherStationDao, WeatherStationVO> implements WeatherStationService {
    @Autowired
    WeatherStationDao weatherStationDao;
    @Autowired
    SensorDao sensorDao;
    public IPage<WeatherStationModel> selectSensorModel(Page<WeatherStationModel> page, String baseName, String sensorName){
        return  weatherStationDao.selectWeatherStationModel(page,baseName,sensorName);
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertByFile(MultipartFile file) throws IOException {
        WeatherStationListener weatherStationListener = new WeatherStationListener(this, sensorDao);
        EasyExcel.read(file.getInputStream(), SensorWrapper.class,weatherStationListener).sheet().doRead();
    }
}
