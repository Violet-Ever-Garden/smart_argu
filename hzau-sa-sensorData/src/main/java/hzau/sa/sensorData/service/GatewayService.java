package hzau.sa.sensorData.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import hzau.sa.sensorData.entity.GatewayDTO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.sensorData.dao.GatewayDao;
import hzau.sa.sensorData.entity.GatewayVO;

import java.util.List;

/**
 * gateway 服务实现类
 * @author lvhao
 * @date 2020-09-03
 */
public interface GatewayService  extends IService<GatewayVO> {

    /**
     * 查询所有的网关
     * @param page
     * @return
     */
    List<GatewayDTO> queryGateway(Page page);

}