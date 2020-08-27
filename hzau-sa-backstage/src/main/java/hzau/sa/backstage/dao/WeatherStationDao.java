package hzau.sa.backstage.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.SenesorModel;
import hzau.sa.backstage.entity.WeatherStationModel;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.WeatherStationVO;
import org.apache.ibatis.annotations.Param;

/**
 * 气象站 Mapper 接口
 * @author haokai
 * @date 2020-08-26
 */
@Mapper
public interface WeatherStationDao extends BaseMapper<WeatherStationVO> {
    IPage<WeatherStationModel> selectWeatherStationModel(Page<WeatherStationModel> page, @Param("baseName") String baseName, @Param("sensorName")String sensorName);
}