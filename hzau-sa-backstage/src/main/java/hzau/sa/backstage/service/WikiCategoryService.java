package hzau.sa.backstage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import hzau.sa.backstage.entity.WikiCategoryDTO;
import hzau.sa.backstage.entity.WikiCategoryVO;

import java.util.List;

/**
 * wikiCategory 服务实现类
 * @author lvhao
 * @date 2020-09-02
 */
public interface WikiCategoryService  extends IService<WikiCategoryVO> {

    /**
     * 新增百科分类
     * @param wikiCategoryName
     * @return
     */
    public boolean insertWikiCategory(String wikiCategoryName);

    /**
     * 查询所有的百科分类
     * @param page
     * @param keyword
     * @return
     */
    public List<WikiCategoryDTO> queryAllWikiCategory(Page page,String keyword);

    /**
     * 根据百科分类主键修改
     * @param wikiCategoryDTO
     * @return
     */
    public boolean updateWikiCategoryById(WikiCategoryDTO wikiCategoryDTO);

}