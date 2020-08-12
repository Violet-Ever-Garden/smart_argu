package hzau.sa.security.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *  VO
 * @author haokai
 * @date 2020-08-10
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LoginVO {
    private String username;
    private String password;
}
