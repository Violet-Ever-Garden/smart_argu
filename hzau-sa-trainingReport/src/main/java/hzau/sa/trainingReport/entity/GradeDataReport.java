package hzau.sa.trainingReport.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GradeDataReport {
    private String gradeName;
    private List<ClassDataReport> classDataReports;
}
