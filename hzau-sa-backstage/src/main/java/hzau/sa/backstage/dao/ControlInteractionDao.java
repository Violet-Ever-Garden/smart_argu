package hzau.sa.backstage.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.ControlInteractionModel;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.ControlInteractionVO;
import org.apache.ibatis.annotations.Param;

/**
 * (修改为自己的说明) Mapper 接口
 * @author haokai
 * @date 2020-08-28
 */
@Mapper
public interface ControlInteractionDao extends BaseMapper<ControlInteractionVO> {

    IPage<ControlInteractionModel> selectControlInteractionModel(Page<ControlInteractionModel> page, @Param("baseName") String baseName,@Param("remoteControlDeviceName") String remoteControlDeviceName);
}