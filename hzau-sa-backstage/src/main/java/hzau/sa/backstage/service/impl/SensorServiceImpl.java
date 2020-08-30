package hzau.sa.backstage.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.SensorWrapper;
import hzau.sa.backstage.dao.SensorDao;
import hzau.sa.backstage.entity.SensorModel;
import hzau.sa.backstage.entity.SensorVO;
import hzau.sa.backstage.listener.SensorListener;
import hzau.sa.backstage.service.SensorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
    public IPage<SensorModel>  selectSensorModel(Page<SensorModel> page, String baseName, String sensorName){
        return  sensorDao.selectSensorModel(page,baseName,sensorName);
    }
    @Transactional(rollbackFor = Exception.class)
    public void insertByFile(MultipartFile file) throws IOException {
        SensorListener sensorListener = new SensorListener(sensorDao,this);
        EasyExcel.read(file.getInputStream(),SensorWrapper.class,sensorListener).sheet().doRead();
    }

    public List<String> selectSensorByBase(String baseName) {
        return sensorDao.selectSensorByBase(baseName);
    }
}
