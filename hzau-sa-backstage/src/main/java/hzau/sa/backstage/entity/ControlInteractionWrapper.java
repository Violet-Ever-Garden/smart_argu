package hzau.sa.backstage.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author haokai
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ControlInteractionWrapper {
    @ExcelIgnore
    private static final long serialVersionUID = 1L;
    @ExcelProperty(value = "远程控制设备名称", index = 1)
    private String remoteControlDeviceName;
    @ExcelProperty(value = "设备编号", index = 2)
    private String deviceNumber;
    @ExcelProperty(value = "类型", index = 3)
    private String deviceTypeName;
    @ExcelProperty(value = "基地名称", index = 4)
    private String baseName;
    @ExcelProperty(value = "区域名称", index = 5)
    private String regionName;


}
