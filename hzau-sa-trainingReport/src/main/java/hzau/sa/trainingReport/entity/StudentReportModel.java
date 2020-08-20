package hzau.sa.trainingReport.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 按班级id与作物id搜索存在调查报告的同学模板，最新提交时间是调查数据的最新时间
 * @author haokai
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentReportModel {
    private String studentId;
    private int cropId;
    private String studentName;
    private String gradeName;
    private String className;
    private LocalDateTime lastModifiedTime;
}
