package hzau.sa.trainingReport.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import hzau.sa.msg.excel.LocalDateTimeConverter;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author LvHao
 * @Description : 导出实训报告excel的返回实体
 * @date 2020-08-20 11:49
 */
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(20)
@Data
public class ExportReport {

    @ExcelProperty(value = "实训报告名称",index = 1)
    private String trainingReportName;

    @ExcelProperty(value = "提交人",index = 2)
    private String studentName;

    @ExcelProperty(value = "年级",index = 3)
    private String gradeName;

    @ExcelProperty(value = "班级",index = 4)
    private String className;

    @ExcelProperty(value = "得分",index = 5)
    private Integer score;

    @ExcelProperty(value = "批次",index = 6)
    private Integer batch;

    @ExcelProperty(value = "提交时间",index = 7,converter = LocalDateTimeConverter.class)
    private LocalDateTime lastModifiedTime;

}
