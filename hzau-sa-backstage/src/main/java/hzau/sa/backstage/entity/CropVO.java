package hzau.sa.backstage.entity;

import com.baomidou.mybatisplus.annotation.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *  VO
 * @author haokai
 * @date 2020-08-12
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("crop")
public class CropVO {

	private static final long serialVersionUID = 1L;


	/**
	 * 
	 */
	@TableId(type=IdType.AUTO)
	private Integer cropId;


	/**
	 * 
	 */
	private String cropName;


	/**
	 * 
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;


	/**
	 * 
	 */
	private String picturePath;

}