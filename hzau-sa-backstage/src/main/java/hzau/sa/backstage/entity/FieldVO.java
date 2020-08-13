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
 *  地块实体类
 * @author wuyihu
 * @date 2020-08-12
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("field")
@ApiModel("地块model")
public class FieldVO {

	private static final long serialVersionUID = 1L;



	@ApiModelProperty("地块id")
	@TableId(type=IdType.AUTO)
	private Integer fieldId;


	/**
	 * 
	 */
	@ApiModelProperty("地块名字")
	private String fieldName;


	/**
	 * 
	 */
	@ApiModelProperty("地块创建时间")
	private LocalDateTime createTime;

}