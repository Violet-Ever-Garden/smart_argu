package hzau.sa.trainingReport.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import hzau.sa.msg.entity.BaseVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * StudentVO
 * @author lvhao
 * @date 2020-08-19
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("student")
public class StudentVO extends BaseVO{

	private static final long serialVersionUID = 1L;


	/**
	 * 主键
	 */
	@TableId(type=IdType.AUTO)
	private String studentId;


	/**
	 * 密码
	 */
	private String password;


	/**
	 * 电话号码
	 */
	private String phoneNumber;


	/**
	 * 姓名
	 */
	private String studentName;


	/**
	 * 年级ID
	 */
	private Integer gradeId;


	/**
	 * 班级ID
	 */
	private Integer classId;


	/**
	 * 是否操作视频监控
	 */
	private Integer isOperatemonitor;


	/**
	 * 是否操作水肥机
	 */
	private Integer isOperatewfm;
}