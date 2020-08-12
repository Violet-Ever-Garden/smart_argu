package hzau.sa.backstage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *  VO
 * @author haokai
 * @date 2020-08-11
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("grade")
public class GradeVO {

	private static final long serialVersionUID = 1L;


	/**
	 * 
	 */
	@TableId(type=IdType.AUTO)
	private int gradeId;


	/**
	 * 
	 */
	private String gradeName;


	/**
	 * 
	 */
	private LocalDateTime operateTime;

}