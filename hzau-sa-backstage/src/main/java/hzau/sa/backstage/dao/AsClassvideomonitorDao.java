package hzau.sa.backstage.dao;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.AsClassvideomonitorVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (修改为自己的说明) Mapper 接口
 * @author haokai
 * @date 2020-08-29
 */
@Mapper
public interface AsClassvideomonitorDao extends BaseMapper<AsClassvideomonitorVO> {
    int queryMonitorIdByName(@Param("monitorName") String monitorName);

    List<String> queryMonitorNamesByClassId(@Param("classId") Integer classId);

    List<Integer> queryMonitorIdsByClassId(@Param("classId")Integer classId);

    List<Integer> queryVidioMonitorIdsByNames(@Param("list") List<String> videoMonitorDeviceName);
}