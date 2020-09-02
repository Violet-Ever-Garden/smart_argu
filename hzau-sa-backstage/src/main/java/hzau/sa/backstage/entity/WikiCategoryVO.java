package hzau.sa.backstage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import hzau.sa.msg.entity.BaseVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * WikiCategoryVO
 * @author lvhao
 * @date 2020-09-02
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("wikiCategory")
public class WikiCategoryVO extends BaseVO{

	private static final long serialVersionUID = 1L;


	/**
	 * 百科分类编号 自增主键
	 */
	@TableId(type=IdType.AUTO)
	private Integer wikiCategoryId;


	/**
	 * 百科分类名称
	 */
	private String wikiCategoryName;


}