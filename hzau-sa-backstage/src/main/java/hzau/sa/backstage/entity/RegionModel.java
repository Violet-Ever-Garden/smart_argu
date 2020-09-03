package hzau.sa.backstage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author wyh17
 * @version 1.0
 * @date 2020/8/28 18:39
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RegionModel {

    private static final long serialVersionUID = 1L;
    @TableId(type= IdType.AUTO)
    private Integer regionId;
    private String schoolName;
    private String baseName;
    private String regionName;
    private String category;
    private String contactPerson;
    private String phoneNumber;
}
