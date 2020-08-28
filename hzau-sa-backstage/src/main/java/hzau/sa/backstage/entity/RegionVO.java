package hzau.sa.backstage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import hzau.sa.msg.entity.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *  VO
 * @author lvhao
 * @date 2020-08-21
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("region")
public class RegionVO extends BaseVO {

	private static final long serialVersionUID = 1L;

	@TableId(type=IdType.UUID)
	private Integer regionId;

	private String regionName;

	private String category;


	private String contactPerson;

	private String phoneNumber;

	private Integer schoolId;

	private Integer baseId;

}