package hzau.sa.backstage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 地块返回类
 * @author wyh17
 * @version 1.0
 * @date 2020/8/21 9:58
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FieldModel {
    private static final long serialVersionUID = 1L;
    private Integer fieldId;
    private String fieldName;
    private String regionName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
