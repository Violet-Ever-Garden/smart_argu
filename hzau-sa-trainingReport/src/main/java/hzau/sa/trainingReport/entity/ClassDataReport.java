package hzau.sa.trainingReport.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
/**
 * 按班级区分的调查数据
 * @author haokai
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClassDataReport {
    private String className;
    private List<StudentDataReport> studentDataReports;
}
