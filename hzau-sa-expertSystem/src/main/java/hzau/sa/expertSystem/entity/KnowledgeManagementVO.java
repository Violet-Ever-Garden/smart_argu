package hzau.sa.expertSystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import hzau.sa.msg.entity.BaseVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *  VO
 * @author wuyihu
 * @date 2020-08-30
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("knowledgeManagement")
public class KnowledgeManagementVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	@TableId(type=IdType.AUTO)
	private Integer knowledgeManageId;
	private String knowledgeManageName;
	private Integer knowledgeCategoryId;
	private String knowledgeIntroduction;
	private String knowledgeContent;
}