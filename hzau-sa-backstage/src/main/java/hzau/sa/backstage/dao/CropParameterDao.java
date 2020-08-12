package hzau.sa.backstage.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.CropParameterModel;
import javafx.scene.control.Pagination;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.CropParameterVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (修改为自己的说明) Mapper 接口
 * @author haokai
 * @date 2020-08-12
 */
@Mapper
public interface CropParameterDao extends BaseMapper<CropParameterVO> {

    @Select("select cropParameterId,cropName,parameterName,sortNumber,cropId" +
            "from cropParameter p left join crop c on p.cropId = c.cropId" +
            "where c.cropId = #{cropId} and p.parameterName like #{keyword} ")
    public List<CropParameterModel> selectCropParameterListPage(Page<CropParameterModel> page , @Param("cropId") int cropId , @Param("keyword") String  keyword);
}