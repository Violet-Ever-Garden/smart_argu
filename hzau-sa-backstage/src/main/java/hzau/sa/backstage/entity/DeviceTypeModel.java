package hzau.sa.backstage.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author haokai
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DeviceTypeModel {

    private Integer deviceTypeId;



    private String deviceTypeName;



    private String deviceTypeUnit;



    private String moduleType;



    private String typeCode;



    private Double minWarning;



    private Double maxWarning;


    private List<Integer> earlyWarningIds;
}
