package hzau.sa.backstage.dao;

import hzau.sa.backstage.entity.EarlyWarningModel;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.EarlyWarningVO;

import java.util.List;

/**
 * (修改为自己的说明) Mapper 接口
 * @author lvhao
 * @date 2020-08-23
 */
@Mapper
public interface EarlyWarningDao extends BaseMapper<EarlyWarningVO> {
    List<EarlyWarningModel> selectEarlyWarningModel();
}