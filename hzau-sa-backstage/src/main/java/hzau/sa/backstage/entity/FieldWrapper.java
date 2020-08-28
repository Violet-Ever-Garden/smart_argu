package hzau.sa.backstage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *  地块实体包装类
 * @author wuyihu
 * @date 2020-08-12
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FieldWrapper{
    private static final long serialVersionUID = 1L;
    @TableId(type=IdType.AUTO)
    private Integer fieldId;
    private String fieldName;
    private String regionName;
}