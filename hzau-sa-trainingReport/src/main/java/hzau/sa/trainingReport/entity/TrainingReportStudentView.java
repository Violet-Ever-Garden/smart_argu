package hzau.sa.trainingReport.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *  VO
 * @author wuyihu
 * @date 2020-08-19
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TrainingReportStudentView {

    private static final long serialVersionUID = 1L;
    private String trainingReportName;
    private Integer batch;
    private String url;
    private String cropName;
    private String fileName;
}