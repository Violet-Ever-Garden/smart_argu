package hzau.sa.sensorData.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.sensorData.entity.GatewayDTO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.sensorData.entity.GatewayVO;

import java.util.List;

/**
 * Gateway Mapper 接口
 * @author lvhao
 * @date 2020-09-03
 */
@Mapper
public interface GatewayDao extends BaseMapper<GatewayVO> {

    /**
     * 查询所有的网关
     * @param page
     * @return
     */
    List<GatewayDTO> queryGateway(Page<GatewayDTO> page);

}