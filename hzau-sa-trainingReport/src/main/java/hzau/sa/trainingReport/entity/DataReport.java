package hzau.sa.trainingReport.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 调查数据的模板
 * @author 郝凯
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "DataReport")
public class DataReport {
    @Id
    String id;

    int dataReportId;

    int cropId;

    String studentId;



    List<CropData> cropDatas;
}
