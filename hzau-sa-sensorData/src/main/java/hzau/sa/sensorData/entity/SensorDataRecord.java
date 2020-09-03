package hzau.sa.sensorData.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * 昆仑海岸数据xml映射类
 * @author haokai
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class SensorDataRecord {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataTime;
    private String gatewayLogo;
    private String sensorName;
    private String channelName;
    private String value;
    public SensorDataRecord(HashMap<String,String> map) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        this.dataTime = LocalDateTime.parse(map.get("data_time"),formatter) ;
        this.gatewayLogo = map.get("gateway_logo");
        this.sensorName = map.get("sensor_name");
        this.channelName = map.get("channel_name");
        this.value = map.get("value");
    }
}