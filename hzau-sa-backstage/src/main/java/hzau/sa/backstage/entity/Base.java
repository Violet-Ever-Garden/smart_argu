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
 * @author wyh
 * @date 2020-08-26
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("base")
public class Base extends BaseVO{

	private static final long serialVersionUID = 1L;
	@TableId(type=IdType.AUTO)
	private Integer baseId;
	private String baseName;
	private String address;
	private String contactPerson;
	private String phoneNumber;
	private Integer schoolId;

	public Base(BaseModel baseModel){
		this.baseId=baseModel.getBaseId();
		this.baseName=baseModel.getBaseName();
		this.address=baseModel.getAddress();
		this.contactPerson=baseModel.getContactPerson();
		this.phoneNumber=baseModel.getPhoneNumber();
	}
}