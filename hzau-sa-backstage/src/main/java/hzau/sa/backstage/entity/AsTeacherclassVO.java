package hzau.sa.backstage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *  VO
 * @author haokai
 * @date 2020-08-13
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("as_teacherclass")
public class AsTeacherclassVO {

	private static final long serialVersionUID = 1L;


	/**
	 * 
	 */
	@TableId(type=IdType.AUTO)
	private Integer asTeacherclassId;


	/**
	 * 
	 */
	private Integer classId;


	/**
	 * 
	 */
	private String teacherId;


	/**
	 * 
	 */
	private Integer gradeId;

}