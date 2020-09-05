package hzau.sa.sensorData.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class GatewayNameAddress {
    private String gatewayName;
    private String gatewayAddress;
}
