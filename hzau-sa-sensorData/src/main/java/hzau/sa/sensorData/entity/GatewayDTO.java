package hzau.sa.sensorData.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LvHao
 * @Description :
 * @date 2020-09-03 21:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GatewayDTO {

    /**
     * 网关编号 自增主键
     */
    @TableId(type= IdType.UUID)
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
