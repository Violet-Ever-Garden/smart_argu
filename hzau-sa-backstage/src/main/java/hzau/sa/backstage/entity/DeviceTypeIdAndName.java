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
public class DeviceTypeIdAndName {

    private Integer deviceTypeId;

    private String deviceTypeName;
}
