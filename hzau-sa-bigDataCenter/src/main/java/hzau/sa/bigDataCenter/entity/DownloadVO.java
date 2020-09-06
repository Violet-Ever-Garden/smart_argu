package hzau.sa.bigDataCenter.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import hzau.sa.msg.entity.BaseVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *  VO
 * @author lvhao
 * @date 2020-09-06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("download")
public class DownloadVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	@TableId(type=IdType.AUTO)
	private Integer fileId;
	private String fileName;
	private String fileIntroduction;
}