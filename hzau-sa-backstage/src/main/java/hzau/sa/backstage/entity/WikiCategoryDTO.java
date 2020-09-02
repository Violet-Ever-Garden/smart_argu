package hzau.sa.backstage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author LvHao
 * @Description :
 * @date 2020-09-02 10:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WikiCategoryDTO {

    private Integer wikiCategoryId;

    private String wikiCategoryName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
