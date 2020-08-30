package hzau.sa.msg.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import hzau.sa.msg.util.JwtUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * BaseVO
 * 
 * @author wucaidao
 * @date 2019年3月16日 下午5:09:36
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class
BaseVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	public String getCurrentUser() {
	    String username = JwtUtils.currentUser();
        return username == null ? "SYS" : username;
		
	}

	@JsonIgnore
	public String getCurrentUserName() {
	    String realName = JwtUtils.currentUser(JwtUtils.CLAIM_REAL_NAME);
        return realName == null ? "SYS" : realName;
	}
	
	/**
	 * 创建人
	 */
	@TableField(fill = FieldFill.INSERT)
	private String createUser;
	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;
	/**
	 * 修改人
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String lastModifiedUser;
	/**
	 * 修改时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastModifiedTime;
}
