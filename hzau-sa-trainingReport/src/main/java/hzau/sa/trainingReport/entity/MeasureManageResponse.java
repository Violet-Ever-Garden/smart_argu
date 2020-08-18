package hzau.sa.trainingReport.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author LvHao
 * @Description :
 * @date 2020-08-18 2:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasureManageResponse {

    /**
     * 返回措施的Id
     */
    private Integer measureManageId;

    /**
     * 返回措施名称
     */
    private String measureName;

    /**
     * 返回措施内容
     */
    private String measureContent;

    /**
     * 返回措施时间
     */
    private String measureTime;

    /**
     * 返回措施图片的路径
     */
    private List<Map> pictureUrls;

}
