package hzau.sa.security.controller;

import cn.hutool.core.util.StrUtil;


import hzau.sa.msg.entity.Result;
import hzau.sa.msg.exception.DataBaseException;
import hzau.sa.msg.util.ResultUtil;
import hzau.sa.security.service.impl.LoginService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 用户登陆controller
 * @author haokai
 */
@RestController
@Slf4j
public class LoginController {

    @Autowired
    LoginService loginService;

    /**
     * 登录
     */
    @ApiOperation(value = "用户登录", notes = "凭用户名密码登录，获取Token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "String")})
    @PostMapping(value = "/login")
    public Result login( String username,String password) {
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return ResultUtil.error("用户名或密码不能为空");
        }
        HashMap<String, Object> result = loginService.login(username, password);
        if(result == null) {
            return new DataBaseException().insertError("登陆失败，账号或密码不正确");
        }
        return ResultUtil.success(result);
    }


    @ApiOperation(value = "token测试", notes = "token测试")
    @RequiresRoles("teacher")
    @GetMapping("/test")
    public Result test(){
        //Subject subject = SecurityUtils.getSubject();
        //System.out.println(subject.hasRole("teacher"));

        HashMap resultMap = new HashMap();
        resultMap.put("msg","成功");
        return ResultUtil.success(resultMap);
    }
    @ApiOperation(value = "用户登出", notes = "清除token有效期")
    @GetMapping("/logout")
    public Result logout(String username){
        loginService.logout(username);
        return ResultUtil.success("成功退出登陆");
    }
}
