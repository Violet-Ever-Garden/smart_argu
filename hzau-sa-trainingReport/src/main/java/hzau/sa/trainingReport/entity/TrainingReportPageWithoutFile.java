package hzau.sa.trainingReport.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author wyh17
 * @version 1.0
 * @date 2020/8/21 13:53
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TrainingReportPageWithoutFile {
    private String cropName;
    private String cropId;
    private Integer trainingReportId;
    private String trainingReportName;
    private String studentId;
    private String studentName;
    private String className;
    private String gradeName;
    private Integer score;
    private String reviewStatus;
    private Integer batch;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
