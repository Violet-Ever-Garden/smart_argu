package hzau.sa.sensorData.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import hzau.sa.msg.excel.LocalDateTimeConverter;
import hzau.sa.sensorData.common.SensorType;
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
@TableName("sensorDataRecord")
public class SensorDataRecord {

    private static final long serialVersionUID = 1L;

    @ExcelIgnore
    @TableId(type= IdType.AUTO)
    private Integer sensorDataRecordId;

    @ExcelProperty(value = "时间",index = 2,converter = LocalDateTimeConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataTime;

    @ExcelIgnore
    private String gatewayLogo;

    @ExcelProperty(value = "传感器名称",index = 0)
    private String sensorName;

    @ExcelIgnore
    private String channelName;

    @ExcelProperty(value = "数值",index = 1)
    private String value;


    public SensorDataRecord(HashMap<String,String> map) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        this.dataTime = LocalDateTime.parse(map.get("data_time"),formatter) ;
        this.gatewayLogo = map.get("gateway_logo");
        this.sensorName = SensorType.sensorType.get(map.get("channel_name"));
        this.channelName = map.get("channel_name");
        this.value = map.get("value");
    }
}