package hzau.sa.backstage.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.VideoMonitorModel;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.VideoMonitorVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * VideoMonitor Mapper 接口
 * @author lvhao
 * @date 2020-08-27
 */
@Mapper
public interface VideoMonitorDao extends BaseMapper<VideoMonitorVO> {

    List<VideoMonitorModel> queryAllVideoMonitor(Page<VideoMonitorModel> page,
                                                 @Param("videoMonitorDeviceName") String videoMonitorDeviceName,
                                                 @Param("deviceNumber") String deviceNumber,
                                                 @Param("baseName") String baseName,
                                                 @Param("regionName") String regionName);

}