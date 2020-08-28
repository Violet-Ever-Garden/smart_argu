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
 * @date 2020-08-25
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("school")
public class SchoolVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	@TableId(type=IdType.AUTO)
	private Integer schoolId;
	private String schoolName;
	private String address;
	private String legalRepresentative;
	private String contactPerson;
	private String phoneNumber;
}