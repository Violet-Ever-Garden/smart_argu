package hzau.sa.expertSystem.service;

import hzau.sa.msg.entity.Result;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.expertSystem.dao.KnowledgeCategoryDao;
import hzau.sa.expertSystem.entity.KnowledgeCategoryVO;

/**
 * knowledgeCategory 服务实现类
 * @author lvhao
 * @date 2020-08-29
 */
public interface KnowledgeCategoryService  {
    public Result addCategory(KnowledgeCategoryVO knowledgeCategoryVO);
    public Result deleteCategories(Integer[] categoryIds);
    public Result updateCategory(KnowledgeCategoryVO knowledgeCategoryVO);
}