package hzau.sa.msg.util;

import cn.hutool.db.nosql.redis.RedisDS;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wyh17
 * @version 1.0
 * @date 2020/8/24 17:31
 */
class RedisUtilTest {

    @Test
    void set() {
        Jedis jedis = RedisDS.create().getJedis();
        jedis.set("123","456");
        jedis.close();
    }
}