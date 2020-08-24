package hzau.sa.backstage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import hzau.sa.msg.entity.BaseVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *  VO
 * @author haokai
 * @date 2020-08-21
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("deviceType")
public class DeviceTypeVO extends BaseVO{

	private static final long serialVersionUID = 1L;


	/**
	 * 
	 */
	@TableId(type=IdType.AUTO)
	private Integer deviceTypeId;


	/**
	 * 
	 */
	private String deviceTypeName;


	/**
	 * 
	 */
	private String deviceTypeUnit;


	/**
	 * 
	 */
	private String moduleType;


	/**
	 * 
	 */
	private String typeCode;


	/**
	 * 
	 */
	private Double minWarning;


	/**
	 * 
	 */
	private Double maxWarning;



}