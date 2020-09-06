package hzau.sa.sensorData.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.sensorData.dao.GatewayDao;
import hzau.sa.sensorData.entity.GatewayDTO;
import hzau.sa.sensorData.entity.GatewayVO;
import hzau.sa.sensorData.service.GatewayService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *  服务实现类
 *
 * @author lvhao
 * @since 2020-09-03
 */
@Service
public class GatewayServiceImpl extends ServiceImpl<GatewayDao, GatewayVO> implements GatewayService {

    @Resource
    private GatewayDao gatewayDao;

    @Override
    public List<GatewayDTO> queryGateway(Page page) {
        return gatewayDao.queryGateway(page);
    }
}
