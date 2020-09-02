package hzau.sa.backstage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LvHao
 * @Description :
 * @date 2020-08-31 8:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoMonitorDTO {

    /**
     * 主键
     */
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
     * 设备类型名称
     */
    private String deviceTypeName;


    /**
     * 基地编号
     */
    private String baseName;


    /**
     * 区域编号
     */
    private String regionName;

}
