package hzau.sa.sensorData.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author wyh17
 * @version 1.0
 * @date 2020/9/4 6:09
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ControlInteractionModel {
    private Integer controlInteractionId;
    private String remoteControlDeviceName;
    private String remoteStatus;
}
