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

    /**
     * 分页查询所有的视频监控设置
     * @param page 分页
     * @param keyword 关键字
     * @param baseName 基地名称
     * @param regionName 区域名称
     * @return
     */
    List<VideoMonitorModel> queryAllVideoMonitor(Page<VideoMonitorModel> page,
                                                 @Param("keyword") String keyword,
                                                 @Param("baseName") String baseName,
                                                 @Param("regionName") String regionName);

    /**
     * 根据基地名称查询主键
     * @param baseName 基地名称
     * @return
     */
    Integer queryBaseIdByName(String baseName);

    /**
     * 根据区域名称查询主键
     * @param regionName 区域名称
     * @return
     */
    Integer queryRegionIdByName(String regionName);

    /**
     * 根据设备名称查询设备Id
     * @param deviceTypeName
     * @return
     */
    Integer queryDeviceTypeIdByName(String deviceTypeName);

}