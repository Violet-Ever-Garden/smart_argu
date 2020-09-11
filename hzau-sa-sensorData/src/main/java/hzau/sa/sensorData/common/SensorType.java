package hzau.sa.sensorData.common;

import java.util.HashMap;

/**
 * 传感器设备通道名称
 * @author haokai
 */
public class SensorType {
    public static HashMap<String,String> sensorType;
    static {
        sensorType = new HashMap<>();
        sensorType.put("1","空气温度");
        sensorType.put("2","空气湿度");
        sensorType.put("3","光照强度");
        sensorType.put("4","土壤温度");
        sensorType.put("5","土壤湿度");
        sensorType.put("6","气压");
        sensorType.put("48","风速");
        sensorType.put("49","风向");
        sensorType.put("50","雨量");
        sensorType.put("68","土壤水分");
        sensorType.put("33","露点");
    }
}
