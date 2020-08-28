package hzau.sa.backstage.entity;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author haokai
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class SensorWrapper {

    @ExcelIgnore
    private static final long serialVersionUID = 1L;
    @ExcelProperty(value = "传感器名称",index = 1)
    private String sensorName;
    @ExcelProperty(value = "传感器类型",index = 2)
    private String deviceTypeName;
    @ExcelProperty(value = "基地名称",index = 3)
    private String baseName;
    @ExcelProperty(value = "区域名称",index = 4)
    private String regionName;
}
