package hzau.sa.trainingReport.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import hzau.sa.msg.entity.BaseVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * MeasuremanageVO
 * @author lvhao
 * @date 2020-08-18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("measuremanage")
public class MeasuremanageVO extends BaseVO{

	private static final long serialVersionUID = 1L;


	/**
	 * 自增主键
	 */
	@TableId(type=IdType.AUTO)
	private Integer measuremanageId;


	/**
	 * 学号
	 */
	private String studentId;


	/**
	 * 措施编号
	 */
	private Integer measureId;


	/**
	 * 作物编号
	 */
	private Integer cropId;


	/**
	 * 措施内容
	 */
	private String measureContent;

}