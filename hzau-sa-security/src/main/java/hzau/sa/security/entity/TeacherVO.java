package hzau.sa.security.entity;

import com.baomidou.mybatisplus.annotation.*;

import hzau.sa.msg.entity.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *  VO
 * @author haokai
 * @date 2020-08-10
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("teacher")
public class TeacherVO extends BaseVO {

	private static final long serialVersionUID = 1L;


	/**
	 * 
	 */
	@TableId
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