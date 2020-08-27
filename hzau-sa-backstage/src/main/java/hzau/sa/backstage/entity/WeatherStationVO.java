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
 * @date 2020-08-26
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("weatherStation")
public class WeatherStationVO extends BaseVO{

	private static final long serialVersionUID = 1L;


	/**
	 * 
	 */
	@TableId(type=IdType.AUTO)
	private Integer weatherStationId;


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



}