package hzau.sa.backstage.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.DiseaseInsectDTO;
import hzau.sa.backstage.entity.DiseaseInsectIndexDTO;
import hzau.sa.backstage.entity.WikiCropTypeDTO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.backstage.entity.DiseaseInsectVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DiseaseInsectMapper 接口
 * @author lvhao
 * @date 2020-09-02
 */
@Mapper
public interface DiseaseInsectDao extends BaseMapper<DiseaseInsectVO> {

    /**
     * 查询所有的病虫害百科
     * @param page
     * @param keyword
     * @param wikiCropTypeId
     * @return
     */
    List<DiseaseInsectIndexDTO> queryAllDiseaseInsect(Page<DiseaseInsectIndexDTO> page,
                                                     @Param("keyword") String keyword,
                                                     @Param("wikiCropTypeId") Integer wikiCropTypeId);

    /**
     * 根据id查询病虫害百科
     * @param diseaseInsectId
     * @return
     */
    DiseaseInsectDTO queryDiseaseInsectById(Integer diseaseInsectId);

}