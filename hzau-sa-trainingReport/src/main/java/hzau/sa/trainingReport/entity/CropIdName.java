package hzau.sa.trainingReport.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author haokai
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CropIdName {
    private int cropId;
    private String cropName;
}
