package hzau.sa.sensorData.entity;

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
 * @date 2020-09-04
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("controlInteraction")
public class ControlInteractionVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	@TableId(type=IdType.UUID)
	private Integer controlInteractionId;
	private String remoteControlDeviceName;
	private String deviceNumber;
	private String belongNode;
	private String deviceAddress;
	private String remoteStatus;
	private String connectStatus;
	private String homeShow;
	private Integer deviceTypeId;
	private Integer baseId;
	private Integer regionId;


}