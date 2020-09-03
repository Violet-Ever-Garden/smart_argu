package hzau.sa.backstage.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.dao.WikiCropTypeDao;
import hzau.sa.backstage.entity.WikiCropTypeDTO;
import hzau.sa.backstage.entity.WikiCropTypeVO;
import hzau.sa.backstage.service.WikiCropTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * WikiCropType服务实现类
 *
 * @author lvhao
 * @since 2020-09-02
 */
@Service
@Slf4j
public class WikiCropTypeServiceImpl extends ServiceImpl<WikiCropTypeDao, WikiCropTypeVO> implements WikiCropTypeService {

    @Resource
    private WikiCropTypeDao wikiCropTypeDao;

    @Override
    public boolean insertWikiCropType(Integer wikiCategoryId, String wikiCropTypName) {

        WikiCropTypeVO wikiCropTypeVO = new WikiCropTypeVO();
        wikiCropTypeVO.setWikiCategoryId(wikiCategoryId);
        wikiCropTypeVO.setWikiCropTypeName(wikiCropTypName);

        try{
            wikiCropTypeDao.insert(wikiCropTypeVO);
            return true;
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }

    }

    @Override
    public List<WikiCropTypeDTO> queryAllWikiCropType(Page page, String keyword,Integer wikiCategoryId) {
        return wikiCropTypeDao.queryAllWikiCropType(page,keyword,wikiCategoryId);
    }

    @Override
    public boolean updateWikiCropType(WikiCropTypeDTO wikiCropTypeDTO) {

        WikiCropTypeVO wikiCropTypeVO = new WikiCropTypeVO();
        wikiCropTypeVO.setWikiCropTypeId(wikiCropTypeDTO.getWikiCropTypeId());
        wikiCropTypeVO.setWikiCropTypeName(wikiCropTypeDTO.getWikiCropTypeName());

        try{
            wikiCropTypeDao.updateById(wikiCropTypeVO);
            return true;
        }catch (Exception e){
            log.info(e.toString());
            throw e;
        }

    }

    @Override
    public List<WikiCropTypeDTO> queryWikiCropType() {
        return wikiCropTypeDao.queryWikiCropType();
    }
}
