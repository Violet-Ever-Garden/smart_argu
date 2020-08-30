package hzau.sa.expertSystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.expertSystem.dao.KnowledgeManagementDao;
import hzau.sa.expertSystem.entity.KnowledgeManagementModel;
import hzau.sa.expertSystem.entity.KnowledgeManagementVO;
import hzau.sa.expertSystem.entity.KnowledgeManagementView;
import hzau.sa.expertSystem.service.KnowledgeManagementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.FileEnum;
import hzau.sa.msg.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuyihu
 * @since 2020-08-30
 */
@Service
public class KnowledgeManagementServiceImpl extends ServiceImpl<KnowledgeManagementDao, KnowledgeManagementVO> implements KnowledgeManagementService {
    @Autowired
    private KnowledgeManagementDao knowledgeManagementDao;
    /**
     * 增加知识库
     * @param knowledgeManagementView
     * @return
     */
    @Override
    public Result addKnowledge(KnowledgeManagementView knowledgeManagementView){
        return ResultUtil.success();
    }

    /**
     * 批量删除知识库
     * @param knowledgeManagementIds
     * @return
     */
    @Override
    public Result deleteKnowledges(Integer[] knowledgeManagementIds){
        if (knowledgeManagementIds.length==0){
            return ResultUtil.error("至少选择一条数据");
        }
        QueryWrapper<KnowledgeManagementVO> knowledgeManagementVOQueryWrapper = new QueryWrapper<>();
        knowledgeManagementVOQueryWrapper.lambda()
                .in(KnowledgeManagementVO::getKnowledgeManageId,knowledgeManagementIds);
        if (knowledgeManagementDao.delete(knowledgeManagementVOQueryWrapper)==0){
            return ResultUtil.error("批量删除失败");
        }
        return ResultUtil.error();
    }

    /**
     * 更新知识库
     * @param knowledgeManagementView
     * @return
     */
    @Override
    public Result updateKnowledge(KnowledgeManagementView knowledgeManagementView){
        return ResultUtil.success();
    }

    /**
     * 分页
     * @param page
     * @param name
     * @param category
     * @return
     */
    @Override
    public IPage<KnowledgeManagementModel> page(Page<KnowledgeManagementModel> page, String name, String category){
        IPage<KnowledgeManagementModel> iPage = knowledgeManagementDao.page(page, "%" + name + "%", category);
        return iPage;
    }

    /**
     * 按id进入知识库界面
     * @param knowledgeManageId
     * @param fileType
     * @return
     */
    @Override
    public KnowledgeManagementView queryKnowledgeById(String knowledgeManageId, String fileType){
        return knowledgeManagementDao.queryKnowledgeById(knowledgeManageId,String.valueOf(FileEnum.KNOWLEDGE));
    }
}
