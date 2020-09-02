package hzau.sa.backstage.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.WikiCategoryDTO;
import hzau.sa.backstage.entity.WikiCropTypeDTO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.WikiCropTypeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * WikiCropTypeMapper 接口
 * @author lvhao
 * @date 2020-09-02
 */
@Mapper
public interface WikiCropTypeDao extends BaseMapper<WikiCropTypeVO> {

    /**
     * 查询所有的百科植物类型
     * @param page
     * @param keyword
     * @param wikiCategoryId
     * @return
     */
    List<WikiCropTypeDTO> queryAllWikiCropType(Page<WikiCropTypeDTO> page,
                                               @Param("keyword") String keyword,
                                               @Param("wikiCategoryId") Integer wikiCategoryId);

}