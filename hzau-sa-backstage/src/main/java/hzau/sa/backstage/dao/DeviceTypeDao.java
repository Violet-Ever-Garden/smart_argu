package hzau.sa.backstage.dao;

import hzau.sa.backstage.entity.DeviceTypeIdAndName;
import hzau.sa.backstage.entity.EarlyWarningModel;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.DeviceTypeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备类型 Mapper 接口
 * @author haokai
 * @date 2020-08-21
 */
@Mapper
public interface DeviceTypeDao extends BaseMapper<DeviceTypeVO> {

    List<DeviceTypeIdAndName> selectDeviceTypeModel(@Param("moduleType") String moduleType);

    List<EarlyWarningModel> selectEarlyWarningByDeviceTypeId(@Param(("deviceTypeId")) int deviceTypeId);
}