package hzau.sa.security.Shiro;


import org.apache.shiro.authc.AuthenticationToken;

/**
 * JWTToken
 * 
 * @author wucaidao
 * @date 2019年3月16日 下午5:10:57
 */
public class JwtToken implements AuthenticationToken {

	private static final long serialVersionUID = 294448930140792013L;
	
	private final String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
