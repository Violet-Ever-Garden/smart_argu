package hzau.sa.backstage.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.SensorModel;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.SensorVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 传感器设备 Mapper 接口
 * @author haokai
 * @date 2020-08-25
 */
@Mapper
public interface SensorDao extends BaseMapper<SensorVO> {
    IPage<SensorModel> selectSensorModel(Page<SensorModel> page, @Param("baseName") String baseName, @Param("sensorName")String sensorName);

    Integer getDeviceTypeIdByName(@Param("deviceTypeName") String  deviceTypeName);

    Integer getBaseIdByName(@Param("baseName")String  baseName);

    Integer getRegionIdByName(@Param("regionName")String  regionName);

    List<String> selectSensorByBase(@Param("baseName") String baseName);
}