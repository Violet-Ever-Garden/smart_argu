package hzau.sa.msg.util;

import cn.hutool.db.nosql.redis.RedisDS;
import hzau.sa.msg.common.Constant;
import hzau.sa.msg.common.RoleConstant;
import redis.clients.jedis.Jedis;

public class RedisUtil {
    public static void set(String username,String token,String role){
        Jedis jedis = RedisDS.create().getJedis();
        jedis.setex(username, Constant.EXPIRE_TIME_SECOND, token);
        jedis.setex(username + RoleConstant.ROLE, Constant.EXPIRE_TIME_SECOND, role);//设置角色
        jedis.close();
    }

    public static void del(String key){
        Jedis jedis = RedisDS.create().getJedis();
        jedis.del(key);
        jedis.del(key+"role");
        jedis.close();
    }
}
