package hzau.sa.backstage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LvHao
 * @Description :
 * @date 2020-08-27 15:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoMonitorModel {

    /**
     * 视频监控设备名称
     */
    private String videoMonitorDeviceName;

    /**
     * 设备编号
     */
    private String deviceNumber;

    /**
     * 视频应用
     */
    private String videoApplication;

    /**
     * 基地名称
     */
    private Integer baseName;

    /**
     * 区域名称
     */
    private Integer regionName;

    /**
     * 基地IP、端口
     */
    private String ipAndPort;

    /**
     * 首页显示
     */
    private String homeShow;
}
