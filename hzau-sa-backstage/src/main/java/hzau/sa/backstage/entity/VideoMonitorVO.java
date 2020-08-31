package hzau.sa.backstage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import hzau.sa.msg.entity.BaseVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * VideoMonitorVO
 * @author lvhao
 * @date 2020-08-27
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("videoMonitor")
public class VideoMonitorVO extends BaseVO{

	private static final long serialVersionUID = 1L;


	/**
	 * 自增主键
	 */
	@TableId(type=IdType.AUTO)
	private Integer videoMonitorId;


	/**
	 * 视频监控设备名称
	 */
	private String videoMonitorDeviceName;


	/**
	 * 设备编号
	 */
	private String deviceNumber;


	/**
	 * 设备IP
	 */
	private String deviceIp;


	/**
	 * 设备端口
	 */
	private String devicePort;


	/**
	 * 设备账号
	 */
	private String deviceAccountNumber;


	/**
	 * 设备密码
	 */
	private String devicePassword;


	/**
	 * 设备通道
	 */
	private String videoChannel;


	/**
	 * 首页显示
	 */
	private String homeShow;


	/**
	 * 视频应用
	 */
	private String videoApplication;


	/**
	 * 是否荧云石
	 */
	@TableField(value = "is_yingshiyun")
	private Integer isYingshiyun;


	/**
	 * APPKey
	 */
	private String appKey;


	/**
	 * Secret
	 */
	private String secret;


	/**
	 * Rtmp 播放地址
	 */
	private String rtmpPlayAddress;


	/**
	 * 基地编号
	 */
	private Integer baseId;


	/**
	 * 区域编号
	 */
	private Integer regionId;

}