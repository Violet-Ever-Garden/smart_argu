package hzau.sa.trainingReport.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import hzau.sa.msg.entity.BaseVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * AsTeacherclassVO
 * @author lvhao
 * @date 2020-08-19
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("as_teacherclass")
public class AsTeacherclassVO extends BaseVO{

	private static final long serialVersionUID = 1L;


	/**
	 * 主键
	 */
	@TableId(type=IdType.AUTO)
	private Integer asTeacherclassId;


	/**
	 * 班级ID
	 */
	private Integer classId;


	/**
	 * 老师工号
	 */
	private String teacherId;


	/**
	 * 年级ID
	 */
	private Integer gradeId;

}