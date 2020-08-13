package hzau.sa.backstage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * GradeVO
 * @author lvhao
 * @date 2020-08-12
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("grade")
public class GradeVO {

	private static final long serialVersionUID = 1L;


	/**
	 * 自增主键
	 */
	@TableId(type=IdType.AUTO)
	private Integer gradeId;


	/**
	 * 名称
	 */
	private String gradeName;


	/**
	 * 创建时间
	 */
	private LocalDateTime operateTime;

}