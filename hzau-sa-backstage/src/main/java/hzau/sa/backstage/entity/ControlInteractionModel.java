package hzau.sa.backstage.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author haokai
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ControlInteractionModel {
    private Integer controlInteractionId;


    private String remoteControlDeviceName;


    private String deviceNumber;


    private String belongNode;


    private String deviceAddress;


    private String remoteStatus;


    private String connectStatus;


    private String homeShow;


    private Integer deviceTypeId;


    private String deviceTypeName;


    private Integer baseId;


    private String baseName;


    private Integer regionId;


    private String regionName;
}
