package hzau.sa.backstage.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("学生model")
public class StudentVO {

	@ExcelIgnore
	private static final long serialVersionUID = 1L;


	/**
	 * 
	 */
	@ApiModelProperty("学号")
	@ExcelProperty(value = "学号",index = 0)
	@TableId(type=IdType.AUTO)
	private String studentId;


	/**
	 * 
	 */
	@ApiModelProperty("密码")
	@ExcelIgnore
	private String password;


	/**
	 * 
	 */
	@ApiModelProperty("手机号")
	@ExcelProperty(value = "手机号",index = 4)
	private String phoneNumber;


	/**
	 * 
	 */
	@ApiModelProperty("姓名")
	@ExcelProperty(value = "姓名",index = 1)
	private String studentName;


	/**
	 * 
	 */
	@ApiModelProperty("年级")
	@ExcelProperty(value = "年级",index = 2)
	private Integer gradeId;


	/**
	 * 
	 */
	@ApiModelProperty("班级")
	@ExcelProperty(value = "班级",index = 3)
	private Integer classId;


	/**
	 * 
	 */
	@ApiModelProperty("监控权限")
	@ExcelIgnore
	private Integer isOperatemonitor;


	/**
	 * 
	 */
	@ApiModelProperty("水肥机权限")
	@ExcelIgnore
	private Integer isOperatewfm;

}