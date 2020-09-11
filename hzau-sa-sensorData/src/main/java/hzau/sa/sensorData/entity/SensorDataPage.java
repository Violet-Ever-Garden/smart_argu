package hzau.sa.sensorData.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SensorDataPage {
    private int current;
    private int size;
    private int total;
    private int pages;
    private HashMap<String , List<SensorDataRecord>> records;
}
