package hzau.sa.backstage.entity;

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
public class WeatherStationModel {
    private Integer weatherStationId;


    private String sensorName;


    private String sensorNumber;


    private String sensorNode;


    private String sensorAddress;


    private String sensorCommunicaton;


    private String remoteStatus;


    private String homeShow;


    private Integer deviceTypeId;


    private String deviceTypeName;


    private Integer baseId;


    private String baseName;


    private Integer regionId;


    private String regionName;
}
