package hzau.sa.backstage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import hzau.sa.backstage.entity.WikiCategoryDTO;
import hzau.sa.backstage.entity.WikiCropTypeDTO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.backstage.dao.WikiCropTypeDao;
import hzau.sa.backstage.entity.WikiCropTypeVO;

import java.util.List;

/**
 * wikiCropType 服务实现类
 * @author lvhao
 * @date 2020-09-02
 */
public interface WikiCropTypeService  extends IService<WikiCropTypeVO> {

    /**
     * 新增百科植物类型
     * @param wikiCategoryId
     * @param wikiCropTypName
     * @return
     */
    public boolean insertWikiCropType(Integer wikiCategoryId,String wikiCropTypName);

    /**
     * 查询所有的百科植物分类
     * @param page
     * @param keyword
     * @param wikiCategoryId
     * @return
     */
    public List<WikiCropTypeDTO> queryAllWikiCropType(Page page, String keyword,Integer wikiCategoryId);

    /**
     * 根据主键更新百科植物分类
     * @param wikiCropTypeDTO
     * @return
     */
    public boolean updateWikiCropType(WikiCropTypeDTO wikiCropTypeDTO);

    /**
     * 查询所有已存在的百科植物分类
     * @return
     */
    public List<WikiCropTypeDTO> queryWikiCropType();

}