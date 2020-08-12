package hzau.sa.security.service.impl;

import cn.hutool.db.nosql.redis.RedisDS;
import hzau.sa.msg.common.Constant;
import hzau.sa.msg.util.JwtUtils;
import hzau.sa.security.Shiro.ShiroKit;
import hzau.sa.security.dao.StudentDao;
import hzau.sa.security.dao.TeacherDao;
import hzau.sa.security.entity.StudentVO;
import hzau.sa.security.entity.TeacherVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashMap;

/**
 * 登陆业务逻辑实现
 * @author haokai
 */
@Service
@Slf4j
public class LoginService {

    @Autowired
    StudentDao studentDao;
    @Autowired
    TeacherDao teacherDao;
    /**
     * 用户登录验证
     *
     * @param username
     * @param password
     * @return UserVO
     */
    public HashMap<String,String> login(String username, String password) {

        StudentVO studentVO = studentDao.selectById(username);
        TeacherVO teacherVO = teacherDao.selectById(username);
        HashMap result = new HashMap();
        if (null != studentVO) {

            String enpw = ShiroKit.md5(password);
            if (!enpw.equals(studentVO.getPassword())) {
                return null;
            }
            log.info("student: "+studentVO.toString());
            String token = JwtUtils.sign(username);
            Jedis jedis = RedisDS.create().getJedis();
            jedis.setex(username, Constant.EXPIRE_TIME_SECOND, token);
            jedis.setex(username + "role", Constant.EXPIRE_TIME_SECOND, "student");//设置角色
            jedis.close();
            result.put("token",token);
            result.put("role","student");
            return result;
        }else if (null != teacherVO){
            String enpw = ShiroKit.md5(password);
            log.info(enpw);
            log.info("teacher: "+teacherVO.toString());
            if (!enpw.equals(teacherVO.getPassword())) {
                return null;
            }
            String token = JwtUtils.sign(username);
            Jedis jedis = RedisDS.create().getJedis();
            jedis.setex(username, Constant.EXPIRE_TIME_SECOND, token);
            if("admin".equals(teacherVO.getType())) {
                jedis.setex(username + "role", Constant.EXPIRE_TIME_SECOND, "admin");
                result.put("role","admin");
            }else {
                jedis.setex(username + "role", Constant.EXPIRE_TIME_SECOND, "teacher");
                result.put("role","teacher");
            }

            result.put("token",token);
            return result;
        }
        return null;
    }


    public void logout(String username){
        String token = JwtUtils.sign(JwtUtils.currentUser(), 0L);
        Jedis jedis = RedisDS.create().getJedis();
        jedis.del(username);
        jedis.del(username+"role");
        jedis.close();
    }
}
