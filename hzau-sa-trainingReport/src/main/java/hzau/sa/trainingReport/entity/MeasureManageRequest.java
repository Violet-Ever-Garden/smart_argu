package hzau.sa.trainingReport.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * @author LvHao
 * @Description : 措施管理
 * @date 2020-08-18 1:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasureManageRequest {

    /**
     * 学生学号
     */
    private String studentId;

    /**
     * 措施
     */
    private String measure;

    /**
     * 作物
     */
    private String crop;

    /**
     * 措施内容
     */
    private String measureContent;

    /**
     * 图片数组
     */
    private MultipartFile[] multipartFiles;

    /**
     * 操作时间
     */
    private LocalDateTime createTime;

}
