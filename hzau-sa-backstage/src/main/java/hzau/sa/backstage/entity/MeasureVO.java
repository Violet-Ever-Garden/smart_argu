package hzau.sa.backstage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import hzau.sa.msg.entity.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *  措施实体类
 * @author wuyihu
 * @date 2020-08-12
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("measure")
public class MeasureVO extends BaseVO {

	private static final long serialVersionUID = 1L;

	@TableId(type=IdType.AUTO)
	private Integer measureId;

	private String measureName;

	public MeasureVO(MeasureWrapper measureWrapper){
		this.measureId=measureWrapper.getMeasureId();
		this.measureName=measureWrapper.getMeasureName();
	}
}