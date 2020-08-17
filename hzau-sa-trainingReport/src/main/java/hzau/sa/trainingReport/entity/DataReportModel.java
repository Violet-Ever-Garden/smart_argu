package hzau.sa.trainingReport.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 前端接受调查数据列表模板
 * @author haokai
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DataReportModel {

    private Integer dataReportId;


    private Integer cropId;


    private String studentId;


    private String studentName;


    private String className;


    private String gradeName;


    private LocalDateTime lastModifiedTime;

}
