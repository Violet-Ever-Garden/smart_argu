package hzau.sa.trainingReport.entity;



import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;


/**
 * 作物单条数据
 * @author haokai
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CropData {

    String cropProperty;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime detectionTime;
    String growthPeriod;
    String process;

    HashMap<String,String> extraParam;
    List<Double> data;
    double average;

}
