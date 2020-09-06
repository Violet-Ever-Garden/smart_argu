package hzau.sa.sensorData.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import hzau.sa.msg.entity.BaseVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * GatewayVO
 * @author lvhao
 * @date 2020-09-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("gateway")
public class GatewayVO extends BaseVO{

	private static final long serialVersionUID = 1L;


	/**
	 * 网关编号 自增主键
	 */
	@TableId(type=IdType.AUTO)
	private Integer gatewayId;


	/**
	 * 网关名称
	 */
	private String gatewayName;


	/**
	 * 网关地址
	 */
	private String gatewayAddress;


}