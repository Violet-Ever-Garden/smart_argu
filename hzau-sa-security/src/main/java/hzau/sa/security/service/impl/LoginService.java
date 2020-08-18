package hzau.sa.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import hzau.sa.msg.dao.FileDao;
import hzau.sa.msg.entity.FileVO;
import hzau.sa.msg.enums.FileEnum;
import hzau.sa.msg.util.JwtUtils;
import hzau.sa.msg.common.RoleConstant;
import hzau.sa.msg.util.RedisUtil;
import hzau.sa.msg.util.ShiroKit;
import hzau.sa.security.dao.LoginStudentDao;
import hzau.sa.security.dao.LoginTeacherDao;
import hzau.sa.security.entity.StudentVO;
import hzau.sa.security.entity.TeacherVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * 登陆业务逻辑实现
 * @author haokai
 */
@Service
@Slf4j
public class LoginService {

    @Autowired
    LoginStudentDao studentDao;
    @Autowired
    LoginTeacherDao teacherDao;
    @Autowired
    FileDao fileDao;
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
            return loginStudentResult(token,RoleConstant.STUDENT,studentVO);
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
                return loginTeacherResult(token,RoleConstant.ADMIN,teacherVO);
            }else {
                RedisUtil.set(username,token,RoleConstant.TEACHER);
                return loginTeacherResult(token,RoleConstant.TEACHER,teacherVO);
            }
        }
        return null;
    }


    public void logout(String username){
        String token = JwtUtils.sign(JwtUtils.currentUser(), 0L);
        RedisUtil.del(username);
    }

    public HashMap<String,Object> loginStudentResult(String token,String role,StudentVO studentVO){
        HashMap result = new HashMap();
        result.put(RoleConstant.TOKEN,token);
        result.put(RoleConstant.ROLE,role);
        result.put("name",studentVO.getStudentName());
        result.put("id",studentVO.getStudentId());

        QueryWrapper<FileVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FileVO::getConnectId,studentVO.getStudentId())
                .eq(FileVO::getFileType, FileEnum.AVATAR);
        FileVO fileVO = fileDao.selectOne(queryWrapper);
        if(fileVO==null){
            result.put("url","");
        }else {
            result.put("url",fileVO.getUrl());
        }

        return result;
    }
    public HashMap<String,Object> loginTeacherResult(String token,String role,TeacherVO teacherVO){
        HashMap result = new HashMap();
        result.put(RoleConstant.TOKEN,token);
        result.put(RoleConstant.ROLE,role);
        result.put("name",teacherVO.getTeacherName());
        result.put("id",teacherVO.getTeacherId());
        QueryWrapper<FileVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FileVO::getConnectId,teacherVO.getTeacherId())
                .eq(FileVO::getFileType, FileEnum.AVATAR);
        FileVO fileVO = fileDao.selectOne(queryWrapper);
        if(fileVO==null){
            result.put("url","");
        }else {
            result.put("url",fileVO.getUrl());
        }
        return result;
    }
}
