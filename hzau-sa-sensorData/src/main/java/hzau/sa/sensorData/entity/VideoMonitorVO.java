package hzau.sa.sensorData.entity;

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
 * @date 2020-09-04
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("videoMonitor")
public class VideoMonitorVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	@TableId(type=IdType.AUTO)
	private Integer videoMonitorId;
	private String videoMonitorDeviceName;
	private String deviceNumber;
	private String deviceIp;
	private String devicePort;
	private String deviceAccountNumber;
	private String devicePassword;
	private String videoChannel;
	private String homeShow;
	private String videoApplication;
	private Boolean isYingshiyun;
	private String appKey;
	private String secret;
	private String rtmpPlayAddress;
	private Integer baseId;
	private Integer regionId;
	private Integer deviceTypeId;

}