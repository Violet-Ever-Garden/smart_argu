package hzau.sa.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

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
@TableName("teacher")
public class TeacherVO {

	private static final long serialVersionUID = 1L;


	/**
	 * 
	 */
	@TableId(type=IdType.UUID)
	private String teacherId;


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
	private String teacherName;


	/**
	 * 
	 */
	private Integer isOperatemonitor;


	/**
	 * 
	 */
	private String type;

}