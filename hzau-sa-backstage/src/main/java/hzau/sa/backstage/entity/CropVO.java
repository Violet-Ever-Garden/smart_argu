package hzau.sa.backstage.entity;

import com.baomidou.mybatisplus.annotation.*;

import hzau.sa.msg.entity.BaseVO;
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
public class CropVO extends BaseVO {

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
	private String picturePath;

}