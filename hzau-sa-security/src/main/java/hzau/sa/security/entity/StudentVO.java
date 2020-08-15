package hzau.sa.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import hzau.sa.msg.entity.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *  VO
 * @author haokai
 * @date 2020-08-10
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("student")
public class StudentVO extends BaseVO {

	private static final long serialVersionUID = 1L;


	/**
	 * 
	 */
	@TableId(type=IdType.UUID)
	private String studentId;


	/**
	 * 
	 */
	private String password;


	/**
	 * 
	 */
	private String phoneNumber;


	/**
	 * 
	 */
	private String studentName;


	/**
	 * 
	 */
	private Integer gradeId;


	/**
	 * 
	 */
	private Integer classId;


	/**
	 * 
	 */
	private Integer isOperatemonitor;


	/**
	 * 
	 */
	private String isOperatewfm;

}