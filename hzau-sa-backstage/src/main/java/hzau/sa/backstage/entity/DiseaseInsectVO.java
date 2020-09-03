package hzau.sa.backstage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import hzau.sa.msg.entity.BaseVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DiseaseInsectVO
 * @author lvhao
 * @date 2020-09-02
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("diseaseInsect")
public class DiseaseInsectVO extends BaseVO{

	private static final long serialVersionUID = 1L;


	/**
	 * 病虫害编号
	 */
	@TableId(type=IdType.AUTO)
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
	 * 百科分类编号
	 */
	private Integer wikiCategoryId;


	/**
	 * 百科植物类型编号
	 */
	private Integer wikiCropTypeId;


	/**
	 * 病虫害简介
	 */
	private String diseaseInsectIntroduction;


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


}