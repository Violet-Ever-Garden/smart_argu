package hzau.sa.msg.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *  VO
 * @author lvhao
 * @date 2020-08-18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("file")
public class FileVO extends BaseVO{

	private static final long serialVersionUID = 1L;


	/**
	 * 文件ID 自增主键
	 */
	@TableId(type=IdType.AUTO)
	private Integer fileId;


	/**
	 * 文件的类型 FileEnum 枚举类
	 */
	private String fileType;


	/**
	 * 连接外键的id
	 */
	private String connectId;


	/**
	 * 文件的绝对路劲
	 */
	private String fileAbsolutePath;


	/**
	 * 文件的映射网络路由
	 */
	private String url;


}