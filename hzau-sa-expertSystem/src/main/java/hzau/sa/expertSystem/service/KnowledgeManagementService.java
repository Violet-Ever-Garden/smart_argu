package hzau.sa.expertSystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.expertSystem.entity.KnowledgeManagementModel;
import hzau.sa.expertSystem.entity.KnowledgeManagementView;
import hzau.sa.msg.entity.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.expertSystem.dao.KnowledgeManagementDao;
import hzau.sa.expertSystem.entity.KnowledgeManagementVO;

/**
 * knowledgeManagement 服务实现类
 * @author wuyihu
 * @date 2020-08-30
 */
public interface KnowledgeManagementService  {
    public Result addKnowledge(KnowledgeManagementView knowledgeManagementView);
    public Result deleteKnowledges(Integer[] knowledgeManagementIds);
    public Result updateKnowledge(KnowledgeManagementView knowledgeManagementView);
    public IPage<KnowledgeManagementModel> page(Page<KnowledgeManagementModel> page,String name,String category);
    public KnowledgeManagementView queryKnowledgeById(String knowledgeManageId,String fileType);
}