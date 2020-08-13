package hzau.sa.backstage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * AsClassmonitorVO
 * @author lvhao
 * @date 2020-08-12
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("as_classmonitor")
public class AsClassmonitorVO {

	private static final long serialVersionUID = 1L;


	/**
	 * 自增主键
	 */
	@TableId(type=IdType.AUTO)
	private Integer asClassmonitorId;


	/**
	 * 班级外键
	 */
	private Integer classId;


	/**
	 * 监视器外键
	 */
	private Integer monitorId;

}