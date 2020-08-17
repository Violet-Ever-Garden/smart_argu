package hzau.sa.trainingReport.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import hzau.sa.msg.entity.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *  VO
 * @author lvhao
 * @date 2020-08-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("dataReport")
public class DataReportVO extends BaseVO {

	private static final long serialVersionUID = 1L;


	/**
	 * 
	 */
	@TableId(type=IdType.AUTO)
	private Integer dataReportId;


	/**
	 * 
	 */
	private Integer cropId;


	/**
	 * 
	 */
	private String studentId;


	private String className;


	private String gradeName;

}