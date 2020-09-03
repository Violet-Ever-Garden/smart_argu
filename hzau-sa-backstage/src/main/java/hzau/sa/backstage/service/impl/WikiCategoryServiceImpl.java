package hzau.sa.backstage.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.dao.WikiCategoryDao;
import hzau.sa.backstage.entity.WikiCategoryDTO;
import hzau.sa.backstage.entity.WikiCategoryVO;
import hzau.sa.backstage.service.WikiCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *  百科分类服务实现类
 *
 *
 * @author lvhao
 * @since 2020-09-02
 */
@Service
@Slf4j
public class WikiCategoryServiceImpl extends ServiceImpl<WikiCategoryDao, WikiCategoryVO> implements WikiCategoryService {

    @Resource
    private WikiCategoryDao wikiCategoryDao;

    @Override
    public boolean insertWikiCategory(String wikiCategoryName) {

        WikiCategoryVO wikiCategoryVO = new WikiCategoryVO();
        wikiCategoryVO.setWikiCategoryName(wikiCategoryName);

        try{
            wikiCategoryDao.insert(wikiCategoryVO);
            return true;
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }
    }

    @Override
    public List<WikiCategoryDTO> queryAllWikiCategory(Page page, String keyword) {
        return wikiCategoryDao.queryAllWikiCategory(page,keyword);
    }

    @Override
    public boolean updateWikiCategoryById(WikiCategoryDTO wikiCategoryDTO) {

        WikiCategoryVO wikiCategoryVO = new WikiCategoryVO();
        wikiCategoryVO.setWikiCategoryId(wikiCategoryDTO.getWikiCategoryId());
        wikiCategoryVO.setWikiCategoryName(wikiCategoryDTO.getWikiCategoryName());

        try{
            wikiCategoryDao.updateById(wikiCategoryVO);
            return true;
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }
    }
}
