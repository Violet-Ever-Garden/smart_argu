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
public class AnalysisModel {
    private String cropId;
    private String cropName;
    private String gradeName;
    private String className;
    private String studentName;
    private String studentId;
    private List<CropData> cropDataList;
}
