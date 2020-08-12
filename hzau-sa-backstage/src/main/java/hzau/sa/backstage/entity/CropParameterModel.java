package hzau.sa.backstage.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *  VO
 * @author haokai
 * @date 2020-08-12
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CropParameterModel {
    private Integer cropParameterId;


    private String cropName;
    /**
     *
     */
    private String parameterName;


    /**
     *
     */
    private Integer sortNumber;


    /**
     *
     */
    private Integer cropId;
}
