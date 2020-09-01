package hzau.sa.expertSystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author wyh17
 * @version 1.0
 * @date 2020/8/30 20:16
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class KnowledgeManagementView{
    private static final long serialVersionUID = 1L;
    private Integer knowledgeManageId;
    private String url;
    private String knowledgeManageName;
    private String knowledgeCategoryName;
    private String knowledgeIntroduction;
    private String knowledgeContent;
    private String createUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}