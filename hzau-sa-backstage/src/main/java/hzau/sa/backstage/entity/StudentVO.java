package hzau.sa.backstage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import hzau.sa.msg.entity.BaseVO;
import hzau.sa.msg.util.ShiroKit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *  学生实体类
 * @author wuyihu
 * @date 2020-08-13
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("student")
public class StudentVO extends BaseVO {


	private static final long serialVersionUID = 1L;


	private static final String DEFAULT_PASSWORD="123456";


	private String studentId;


	private String password;


	private String phoneNumber;


	private String studentName;


	private Integer gradeId;


	private Integer classId;


	private Integer isOperatemonitor;


	private Integer isOperatewfm;

	public StudentVO(StudentWrapper studentWrapper){

		if (studentWrapper.getIsOperatemonitor().equals("否")){
			this.setIsOperatemonitor(0);
		}else {
			this.setIsOperatemonitor(1);
		}

		if (studentWrapper.getIsOperatewfm().equals("否")){
			this.setIsOperatewfm(0);
		}else {
			this.setIsOperatewfm(1);
		}

		if (studentWrapper.getPassword()==null){
			this.password= ShiroKit.md5(StudentVO.DEFAULT_PASSWORD);
		}else {
			this.password=ShiroKit.md5(studentWrapper.getPassword());
		}

		this.studentName= studentWrapper.getStudentName();
		this.phoneNumber= studentWrapper.getPhoneNumber();
		this.studentId= studentWrapper.getStudentId();
	}
}