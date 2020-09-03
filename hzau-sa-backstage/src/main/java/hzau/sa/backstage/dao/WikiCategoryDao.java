package hzau.sa.backstage.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.VideoMonitorModel;
import hzau.sa.backstage.entity.WikiCategoryDTO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.WikiCategoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * WikiCategoryMapper 接口
 * @author lvhao
 * @date 2020-09-02
 */
@Mapper
public interface WikiCategoryDao extends BaseMapper<WikiCategoryVO> {

    /**
     * 查询所有的百科分类
     * @param page
     * @param keyword
     * @return
     */
    List<WikiCategoryDTO> queryAllWikiCategory(Page<WikiCategoryDTO> page,
                                               @Param("keyword") String keyword);

}