package hzau.sa.backstage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * ClassVO
 * @author lvhao
 * @date 2020-08-12
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("class")
public class ClassVO {

	private static final long serialVersionUID = 1L;


	/**
	 * 自增主键
	 */
	@TableId(type=IdType.AUTO)
	private Integer classId;


	/**
	 * 班级名称
	 */
	private String className;


	/**
	 * 年级
	 */
	private Integer gradeId;

}