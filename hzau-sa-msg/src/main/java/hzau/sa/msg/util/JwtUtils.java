package hzau.sa.msg.util;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import hzau.sa.msg.common.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
@Slf4j
public class JwtUtils {
    public static final String CLAIM_USER_NAME = "userName";
    private static final String SECRET = "hzau_smart_agriculture";

    /**
     * 过期时间8小时
     */
    private static final long EXPIRE_TIME = 8 * 60 * 60 * 1000;

    /**
     * 校验token是否正确
     *
     * @param token密钥
     * @return 是否正确
     */
    public static boolean verify(String token, String username) {
        // 根据密码生成JWT效验器
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTVerifier verifier = JWT.require(algorithm).withClaim(CLAIM_USER_NAME, username).build();
        // 效验TOKEN
        verifier.verify(token);
        return true;
    }

    /**
     * 获得token中的用户名
     */
    public static String getUsername(String token) {
        return getClaimValue(token, CLAIM_USER_NAME);
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     */
    public static String getClaimValue(String token, String key) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(key).asString();
        } catch (JWTDecodeException e) {
            log.warn(e.getMessage());
            return null;
        }
    }

    /**
     * 生成签名
     *
     * @param 用户信息...
     * @return 加密的token
     */
    public static String sign(String userName) {
        Date expiresDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        // 附带user信息
        return JWT.create().withClaim(CLAIM_USER_NAME, userName).withExpiresAt(expiresDate)
                .sign(algorithm);
    }

    /**
     * 生成指定过期时间的签名
     *
     * @param 用户信息...
     * @return 加密的token
     */
    public static String sign(String userName, Long expireTime) {
        Date expiresDate = new Date(System.currentTimeMillis() + expireTime);
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create().withClaim(CLAIM_USER_NAME, userName).withExpiresAt(expiresDate).sign(algorithm);
    }

    /**
     * 获取当前用户名
     */
    public static String currentUser() {
        return currentUser(CLAIM_USER_NAME);
    }

    /**
     * 获取当前用户信息
     */
    public static String currentUser(String key) {
        String value = null;
        try {
            ServletRequestAttributes servletRequestAttributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (null != servletRequestAttributes) {
                HttpServletRequest request = servletRequestAttributes.getRequest();
                String token = getRequestToken(request);
                if (StrUtil.isNotBlank(token)) {
                    value = getClaimValue(token, key);
                }
            }
        } catch (Exception ignored) {
            log.warn("Getting Value Exceptions from Token.", ignored);
        }
        return value;
    }

    /**
     * 获取请求的token
     */
    public static String getRequestToken(HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader(Constant.AUTHORIZATION_HEADER);
        if (StrUtil.isBlank(token)) {
            token = httpRequest.getParameter(Constant.AUTHORIZATION_HEADER);
        }
        return token;
    }
}
