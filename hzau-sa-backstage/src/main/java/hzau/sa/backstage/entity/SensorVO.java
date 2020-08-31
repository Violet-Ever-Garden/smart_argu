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
 * @author haokai
 * @date 2020-08-25
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sensor")
public class SensorVO extends BaseVO{

	private static final long serialVersionUID = 1L;


	/**
	 * 
	 */
	@TableId(type=IdType.AUTO)
	private Integer sensorId;


	/**
	 * 
	 */
	private String sensorName;


	/**
	 * 
	 */
	private String sensorNumber;


	/**
	 * 
	 */
	private String sensorNode;


	/**
	 * 
	 */
	private String sensorAddress;


	/**
	 * 
	 */
	private String sensorCommunicaton;


	/**
	 * 
	 */
	private String remoteStatus;


	/**
	 * 
	 */
	private String homeShow;


	/**
	 * 
	 */
	private Integer deviceTypeId;


	/**
	 * 
	 */
	private Integer baseId;


	/**
	 * 
	 */
	private Integer regionId;

	public SensorVO(SensorWrapper sensorWrapper){
		this.sensorName = sensorWrapper.getSensorName();
		this.setRemoteStatus("禁用");
		this.setHomeShow("隐藏");
	}

}