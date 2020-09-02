package hzau.sa.backstage.template;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

/**
 * @author LvHao
 * @Description :
 * @date 2020-08-31 10:17
 */
@ContentRowHeight(30)
@HeadRowHeight(20)
@ColumnWidth(30)
@Data
public class VideoMonitorTemplate {

    @ExcelProperty(value = "视频监控设备名称",index = 1)
    private String videoMonitorDeviceName;

    @ExcelProperty(value = "设备编号",index = 2)
    private String deviceNumber;

    @ExcelProperty(value = "类型",index = 3)
    private String deviceTypeName;

    @ExcelProperty(value = "基地名称",index = 4)
    private String baseName;

    @ExcelProperty(value = "区域名称",index = 5)
    private String regionName;

}
