package hzau.sa.msg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author LvHao
 * @Description :数据库记录日志的表
 * @date 2020-08-08 18:14
 */
@Data
@TableName("log")
public class LogVO implements Serializable {
    private static final long serialVersionUID = -8049292804271908263L;
    /**
     * 主键id
     */
    @TableId(value = "log_id",type= IdType.UUID)
    private String logId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户操作
     */
    private String operation;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 请求参数
     */
    private String params;
    /**
     * 执行时长(毫秒)
     */
    private Long time;
    /**
     * IP地址
     */
    private String ip;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 返回码
     */
    @TableField(value = "ret_code")
    private String retCode;
    /**
     * 返回信息
     */
    @TableField(value = "ret_msg")
    private String retMsg;
    /**
     * 用户客户端信息
     */
    @TableField(value = "user_agent")
    private String userAgent;

}
