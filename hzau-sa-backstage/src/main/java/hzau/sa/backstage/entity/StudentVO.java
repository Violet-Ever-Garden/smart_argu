package hzau.sa.backstage.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;

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
public class StudentVO {

	@ExcelIgnore
	private static final long serialVersionUID = 1L;

	@ExcelIgnore
	private static final String DEFAULT_PASSWORD="123456";

	/**
	 * 
	 */
	@ExcelProperty(value = "学号",index = 0)
	private String studentId;


	/**
	 * 
	 */
	@ExcelIgnore
	private String password;


	/**
	 * 
	 */
	@ExcelProperty(value = "手机号",index = 4)
	private String phoneNumber;


	/**
	 * 
	 */
	@ExcelProperty(value = "姓名",index = 1)
	private String studentName;


	/**
	 * 
	 */
	@ExcelProperty(value = "年级",index = 2)
	private Integer gradeId;


	/**
	 * 
	 */
	@ExcelProperty(value = "班级",index = 3)
	private Integer classId;


	/**
	 * 
	 */
	@ExcelIgnore
	private Integer isOperatemonitor;


	/**
	 * 
	 */
	@ExcelIgnore
	private Integer isOperatewfm;

	public StudentVO(StudentWrapper studentWrapper){
		this.studentName= studentWrapper.getStudentName();

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

		this.phoneNumber= studentWrapper.getPhoneNumber();
		this.studentId= studentWrapper.getStudentId();
		this.studentId=studentWrapper.getStudentId();
		this.classId=0;
		this.setPassword(StudentVO.DEFAULT_PASSWORD);
		this.gradeId=0;
	}
}