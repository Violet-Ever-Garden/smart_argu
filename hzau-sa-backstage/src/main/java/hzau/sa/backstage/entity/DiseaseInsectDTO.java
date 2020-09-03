package hzau.sa.backstage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author LvHao
 * @Description :
 * @date 2020-09-02 22:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiseaseInsectDTO {

    /**
     * 病虫害编号
     */
    private Integer diseaseInsectId;


    /**
     * 病虫害名称
     */
    private String diseaseInsectName;


    /**
     * 病虫害别名
     */
    private String diseaseInsectAlias;


    /**
     * 病虫害标示
     */
    private String diseaseInsectLabel;


    /**
     * 百科植物分类名称
     */
    private String wikiCropTypeName;


    /**
     * 病虫害简介
     */
    private String diseaseInsectIntroduction;

    private List<Map> pictureUrls;


    /**
     * 植物症状
     */
    private String plantSymptom;


    /**
     * 形态特征
     */
    private String morphologyFeature;


    /**
     * 生活习性
     */
    private String livingHabit;


    /**
     * 传播路径
     */
    private String propagationPath;


    /**
     * 发生规律
     */
    private String occurrenceRegularity;


    /**
     *农业防治
     */
    private String agricultureControl;


    /**
     * 化学防治
     */
    private String chemistryControl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private String createUser;

}
