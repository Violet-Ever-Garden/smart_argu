package hzau.sa.backstage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *  VO
 * @author wuyihu
 * @date 2020-08-12
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("measure")
@ApiModel("措施modle")
public class MeasureVO {

	private static final long serialVersionUID = 1L;


	/**
	 * 
	 */
	@ApiModelProperty("措施id")
	@TableId(type=IdType.UUID)
	private Integer measureId;


	/**
	 * 
	 */
	@ApiModelProperty("措施名字")
	private String measureName;


	/**
	 * 
	 */
	@ApiModelProperty("措施的时间")
	private LocalDateTime createTime;

}