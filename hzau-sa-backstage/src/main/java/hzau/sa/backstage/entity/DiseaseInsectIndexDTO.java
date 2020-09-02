package hzau.sa.backstage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author LvHao
 * @Description :
 * @date 2020-09-02 21:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiseaseInsectIndexDTO {

    private Integer diseaseInsectId;

    private String diseaseInsectName;

    private String diseaseInsectAlias;

    private String wikiCropTypeName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private String createUser;

}
