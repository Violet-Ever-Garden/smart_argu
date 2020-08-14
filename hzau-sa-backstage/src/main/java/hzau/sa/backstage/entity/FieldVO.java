package hzau.sa.backstage.entity;

import com.baomidou.mybatisplus.annotation.*;

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
public class FieldVO {

	private static final long serialVersionUID = 1L;



	@TableId(type=IdType.AUTO)
	private Integer fieldId;


	/**
	 * 
	 */
	private String fieldName;


	/**
	 * 
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

}