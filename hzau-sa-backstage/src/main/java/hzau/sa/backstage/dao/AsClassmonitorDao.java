package hzau.sa.backstage.dao;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.AsClassmonitorVO;

import java.util.List;

/**
 * 班级关联监视器 Mapper 接口
 * @author lvhao
 * @date 2020-08-12
 */
@Mapper
public interface AsClassmonitorDao extends BaseMapper<AsClassmonitorVO> {

    /**
     * 按照classId查询所有监视器
     * @param classId
     * @return
     */
    List<String> queryMonitorIdsByClassId(Integer classId);

    /**
     * 根据视频监控查询id
     * @param monitorName
     * @return
     */
    Integer queryMonitorIdByName(String monitorName);

}