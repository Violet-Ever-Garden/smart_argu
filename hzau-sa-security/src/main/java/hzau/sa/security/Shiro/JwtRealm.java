package hzau.sa.security.Shiro;

import cn.hutool.db.nosql.redis.RedisDS;
import hzau.sa.msg.util.JwtUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;


@Slf4j
@Service
public class JwtRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = principalCollection.getPrimaryPrincipal().toString();
        log.info("授权： "+username);
        //String userneme = JwtUtils.getUsername(token);
        //log.info("授权username: "+userneme);
        Jedis jedis = RedisDS.create().getJedis();
        String role = jedis.get(username+"role");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //Subject subject = SecurityUtils.getSubject();
        log.info(role);
        simpleAuthorizationInfo.addRole(role);
        //new Authority();
        //simpleAuthorizationInfo.addStringPermissions(Authority.getAuthority(role));
        return simpleAuthorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //从token中获取用户名
        String token = (String)authenticationToken.getCredentials();
        String username = JwtUtils.getUsername(token);
        log.info("认证username : "+username);
        if (username == null) {
            log.warn("Invalid User, Invalid Token");
            throw new AuthenticationException("token invalid");
        }
        //从redis中获取
        Jedis jedis = RedisDS.create().getJedis();
        String  cacheToken = jedis.get(username);
        jedis.close();
        //System.out.println("cacheToken : ===="+cacheToken);
        //System.out.println("token : ===="+token);
        if (cacheToken == null) {
            log.warn("Invalid User, Invalid Token");
            throw new AuthenticationException("token invalid");
        }
        return new SimpleAuthenticationInfo(username,token,getName());
    }
}
