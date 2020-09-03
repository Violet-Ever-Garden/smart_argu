package hzau.sa.expertSystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import hzau.sa.msg.entity.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author wyh17
 * @version 1.0
 * @date 2020/8/30 19:31
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class KnowledgeManagementModel{
    private static final long serialVersionUID = 1L;
    private Integer knowledgeManageId;
    private String knowledgeManageName;
    private String knowledgeIntroduction;
    private String knowledgeCategoryName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private String createUser;
}