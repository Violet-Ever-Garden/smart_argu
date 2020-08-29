package hzau.sa.trainingReport.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
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
public class TrainingReportTeacherView {

    private static final long serialVersionUID = 1L;
    private String StudentName;
    private String teacherName;
    private String className;
    private String gradeName;
    private String trainingReportName;
    private Integer batch;
    private String url;
    private String comments;
    private Integer score;
    private String cropName;
}