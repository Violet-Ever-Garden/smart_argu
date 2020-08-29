package hzau.sa.backstage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import hzau.sa.msg.entity.BaseVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *  VO
 * @author wuyihu
 * @date 2020-08-28
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("region")
public class RegionVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	@TableId(type=IdType.AUTO)
	private Integer regionId;
	private Integer schoolId;
	private Integer baseId;
	private String regionName;
	private String category;
	private String contactPerson;
	private String phoneNumber;

	public RegionVO(RegionModel regionModel){
		this.regionId=regionModel.getRegionId();
		this.regionName=regionModel.getRegionName();
		this.category=regionModel.getCategory();
		this.contactPerson=regionModel.getContactPerson();
		this.phoneNumber=regionModel.getPhoneNumber();
	}
}