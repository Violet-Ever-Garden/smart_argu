package hzau.sa.backstage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import hzau.sa.backstage.entity.DiseaseInsectDTO;
import hzau.sa.backstage.entity.DiseaseInsectIndexDTO;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.backstage.dao.DiseaseInsectDao;
import hzau.sa.backstage.entity.DiseaseInsectVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * diseaseInsect服务实现类
 * @author lvhao
 * @date 2020-09-02
 */
public interface DiseaseInsectService  extends IService<DiseaseInsectVO> {

    /**
     * 增加病虫害白百科
     * @param diseaseInsectVO
     * @param files
     * @return
     * @throws IOException
     */
    public boolean insertDiseaseInsect(DiseaseInsectVO diseaseInsectVO, MultipartFile[] files) throws IOException;

    /**
     * 查询所有的病虫害百科
     * @param page
     * @param keyword
     * @param wikiCropTypeId
     * @return
     */
    public List<DiseaseInsectIndexDTO> queryAllDiseaseInsect(Page page,String keyword,Integer wikiCropTypeId);

    /**
     * 根据id查询病虫害百科
     * @param diseaseInsectId
     * @return
     */
    public DiseaseInsectDTO queryDiseaseInsectById(Integer diseaseInsectId);

    /**
     * 更新病虫害百科
     * @param diseaseInsectVO
     * @param files
     * @param ids
     * @return
     * @throws IOException
     */
    public boolean updateDiseaseInsect(DiseaseInsectVO diseaseInsectVO,MultipartFile[] files,String[] ids) throws IOException;

    /**
     * 单个删除
     * @param diseaseInsectId
     * @return
     */
    public boolean deleteDiseaseInsectById(Integer diseaseInsectId);

    /**
     * 批量删除
     * @param diseaseInsectIds
     * @return
     */
    public boolean deleteDiseaseInsectByIds(String[] diseaseInsectIds);

}