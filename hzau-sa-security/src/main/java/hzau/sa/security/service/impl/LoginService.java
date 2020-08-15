package hzau.sa.security.service.impl;

import cn.hutool.db.nosql.redis.RedisDS;
import hzau.sa.msg.common.Constant;
import hzau.sa.msg.util.JwtUtils;
import hzau.sa.msg.common.RoleConstant;
import hzau.sa.msg.util.RedisUtil;
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
    public HashMap<String, Object> login(String username, String password) {

        StudentVO studentVO = studentDao.selectById(username);
        TeacherVO teacherVO = teacherDao.selectById(username);
        if (null != studentVO) {

            String enpw = ShiroKit.md5(password);
            if (!enpw.equals(studentVO.getPassword())) {
                return null;
            }
            log.info("student: "+studentVO.toString());
            String token = JwtUtils.sign(username,studentVO.getStudentName());
            RedisUtil.set(username,token,RoleConstant.STUDENT);
            return loginResult(token,RoleConstant.STUDENT,studentVO);
        }else if (null != teacherVO){
            String enpw = ShiroKit.md5(password);
            log.info(enpw);
            log.info("teacher: "+teacherVO.toString());
            if (!enpw.equals(teacherVO.getPassword())) {
                return null;
            }
            String token = JwtUtils.sign(username,teacherVO.getTeacherName());
            if(RoleConstant.ADMIN.equals(teacherVO.getType())) {
                RedisUtil.set(username,token,RoleConstant.ADMIN);
                return loginResult(token,RoleConstant.ADMIN,teacherVO);
            }else {
                RedisUtil.set(username,token,RoleConstant.TEACHER);
                return loginResult(token,RoleConstant.TEACHER,teacherVO);
            }
        }
        return null;
    }


    public void logout(String username){
        String token = JwtUtils.sign(JwtUtils.currentUser(), 0L);
        RedisUtil.del(username);
    }

    public HashMap<String,Object> loginResult(String token,String role,Object user){
        HashMap result = new HashMap();
        result.put(RoleConstant.TOKEN,token);
        result.put(RoleConstant.ROLE,role);
        result.put("UserInfo",user);
        return result;
    }
}
