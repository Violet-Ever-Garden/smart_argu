package hzau.sa.expertSystem.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.expertSystem.entity.KnowledgeManagementModel;
import hzau.sa.expertSystem.entity.KnowledgeManagementView;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.expertSystem.entity.KnowledgeManagementVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * (修改为自己的说明) Mapper 接口
 * @author wuyihu
 * @date 2020-08-30
 */
@Repository
@Mapper
public interface KnowledgeManagementDao extends BaseMapper<KnowledgeManagementVO> {
    public IPage<KnowledgeManagementModel> page(Page<KnowledgeManagementModel> page, @Param("name")String name,@Param("category")String category);
    public KnowledgeManagementView queryKnowledgeById(@Param("knowledgeManageId")Integer knowledgeManageId,@Param("fileType")String fileType);
    public Integer queryCategoryIdByName(@Param("name")String name);
}