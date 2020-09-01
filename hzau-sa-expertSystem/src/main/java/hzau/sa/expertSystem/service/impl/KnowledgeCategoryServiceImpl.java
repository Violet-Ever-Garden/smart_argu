package hzau.sa.expertSystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import hzau.sa.expertSystem.entity.KnowledgeCategoryVO;
import hzau.sa.expertSystem.dao.KnowledgeCategoryDao;
import hzau.sa.expertSystem.service.KnowledgeCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lvhao
 * @since 2020-08-29
 */
@Service
public class KnowledgeCategoryServiceImpl extends ServiceImpl<KnowledgeCategoryDao, KnowledgeCategoryVO> implements KnowledgeCategoryService {
    @Autowired
    private KnowledgeCategoryDao knowledgeCategoryDao;
    /**
     * 增加知识库分类
     * @return
     */
    @Override
    public Result addCategory(KnowledgeCategoryVO knowledgeCategoryVO){
        //判断重名
        if (isCategoryExist(knowledgeCategoryVO.getKnowledgeCategoryName())){
            return ResultUtil.error("分类名重复");
        }
        //插入
        if (knowledgeCategoryDao.insert(knowledgeCategoryVO)==0){
            return ResultUtil.error("增加分类失败");
        }
        return ResultUtil.success();
    }

    /**
     * 批量删除知识库分类
     * @param categoryIds
     * @return
     */
    @Override
    public Result deleteCategories(Integer[] categoryIds){
        if(categoryIds.length==0){
            return ResultUtil.error("至少选择一项");
        }
        QueryWrapper<KnowledgeCategoryVO> knowledgeCategoryVOQueryWrapper = new QueryWrapper<>();
        knowledgeCategoryVOQueryWrapper.lambda().in(KnowledgeCategoryVO::getKnowledgeCategoryId,Arrays.asList(categoryIds));
        if (knowledgeCategoryDao.delete(knowledgeCategoryVOQueryWrapper)==0){
            return ResultUtil.error();
        }
        return ResultUtil.success();
    }

    /**
     * 更新知识库分类
     * @return
     */
    @Override
    public Result updateCategory(KnowledgeCategoryVO knowledgeCategoryVO){
        QueryWrapper<KnowledgeCategoryVO> knowledgeCategoryVOQueryWrapper = new QueryWrapper<>();
        knowledgeCategoryVOQueryWrapper.lambda().eq(KnowledgeCategoryVO::getKnowledgeCategoryId,knowledgeCategoryVO.getKnowledgeCategoryId());
        KnowledgeCategoryVO knowledgeCategoryVOSelect = knowledgeCategoryDao.selectOne(knowledgeCategoryVOQueryWrapper);

        //判断重名
        if (isCategoryExist(knowledgeCategoryVO.getKnowledgeCategoryName()) && (!knowledgeCategoryVOSelect.getKnowledgeCategoryName().equals(knowledgeCategoryVO.getKnowledgeCategoryName()))){
            return ResultUtil.error("分类名重复");
        }
        //插入
        if (knowledgeCategoryDao.updateById(knowledgeCategoryVO)==0){
            return ResultUtil.error("更新分类失败");
        }
        return ResultUtil.success();
    }

    /**
     * 判断重名
     * @param name
     * @return
     */
    public boolean isCategoryExist(String name){
        QueryWrapper<KnowledgeCategoryVO> knowledgeCategoryVOQueryWrapper = new QueryWrapper<>();
        knowledgeCategoryVOQueryWrapper.lambda().eq(KnowledgeCategoryVO::getKnowledgeCategoryName,name);
        KnowledgeCategoryVO knowledgeCategoryVO = knowledgeCategoryDao.selectOne(knowledgeCategoryVOQueryWrapper);

        if (knowledgeCategoryVO==null){
            return false;
        }
        return true;
    }
}
