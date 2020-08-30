package hzau.sa.backstage.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.backstage.dao.ControlInteractionDao;
import hzau.sa.backstage.dao.SensorDao;
import hzau.sa.backstage.entity.ControlInteractionModel;
import hzau.sa.backstage.entity.ControlInteractionVO;
import hzau.sa.backstage.entity.ControlInteractionWrapper;
import hzau.sa.backstage.entity.SensorWrapper;
import hzau.sa.backstage.listener.ControlInteractionListener;
import hzau.sa.backstage.listener.SensorListener;
import hzau.sa.backstage.service.ControlInteractionService;
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
 * @author haokai
 * @since 2020-08-28
 */
@Service
public class ControlInteractionServiceImpl extends ServiceImpl<ControlInteractionDao, ControlInteractionVO> implements ControlInteractionService {
    @Autowired
    ControlInteractionDao controlInteractionDao;
    @Autowired
    SensorDao sensorDao;
    public IPage<ControlInteractionModel> selectControlInteractionModel(Page<ControlInteractionModel> page, String baseName, String remoteControlDeviceName) {
        return  controlInteractionDao.selectControlInteractionModel(page,baseName,remoteControlDeviceName);
    }
    @Transactional(rollbackFor = Exception.class)
    public void insertByFile(MultipartFile file) throws IOException {
        ControlInteractionListener controlInteractionListener = new ControlInteractionListener(this,sensorDao);
        EasyExcel.read(file.getInputStream(), ControlInteractionWrapper.class,controlInteractionListener).sheet().doRead();

    }

    public List<String> selectWaterFertilizerMachineByBase(String baseName) {
        return controlInteractionDao.selectWaterFertilizerMachineByBase(baseName);
    }
}
