package hzau.sa.sensorData.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.sensorData.entity.GatewayDTO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.sensorData.entity.GatewayVO;
import org.apache.ibatis.annotations.Param;

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
     * @param keyword
     * @return
     */
    List<GatewayDTO> queryGateway(Page<GatewayDTO> page, @Param("keyword")String keyword);

    /**
     * 查询所有网关号
     * @return 网关号列表
     */
    List<String> queryAll();
}