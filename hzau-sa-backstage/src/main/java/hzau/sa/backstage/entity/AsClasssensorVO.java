package hzau.sa.backstage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import hzau.sa.msg.entity.BaseVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *  VO
 * @author lvhao
 * @date 2020-08-29
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("as_classsensor")
public class AsClasssensorVO extends BaseVO{

	private static final long serialVersionUID = 1L;


	/**
	 * 
	 */
	@TableId(type=IdType.AUTO)
	private Integer asClasssensorId;


	/**
	 * 
	 */
	private Integer classId;


	/**
	 * 
	 */
	private Integer sensorId;



}