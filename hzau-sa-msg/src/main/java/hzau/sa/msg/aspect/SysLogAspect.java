package hzau.sa.msg.aspect;

import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import hzau.sa.msg.exception.DataBaseException;
import hzau.sa.msg.annotation.SysLog;
import hzau.sa.msg.entity.LogVO;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.CodeType;
import hzau.sa.msg.enums.LogType;
import hzau.sa.msg.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author LvHao
 * @Description : 系统日志，切面处理类
 * @date 2020-08-08 17:57
 */
@Slf4j
@Aspect
@Component
public class SysLogAspect {

    @Resource
    private LogService logService;

    @Pointcut("@annotation(hzau.sa.msg.annotation.SysLog)")
    public void logPointCut() {}

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        long beginTime = System.currentTimeMillis();
        LogVO logVO = new LogVO();
        Object result = null;
        try{

            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();

            SysLog sysLog = method.getAnnotation(SysLog.class);
            logVO.setOperation(sysLog.prefix());

            // 请求的方法名
            String className = point.getTarget().getClass().getName();
            String methodName = signature.getName();
            logVO.setMethod(className + "." + methodName + "()");

            // 请求的参数
            Object object = Arrays.stream(point.getArgs())
                    .filter(t -> !(t instanceof ServletRequest) && !(t instanceof ServletResponse) && !(t instanceof MultipartFile)
                            && !(t instanceof HttpSession) && !(t instanceof Model) && !(t instanceof BindingResult))
                    .collect(Collectors.toList());
            String params = JSON.toJSONString(object);
            logVO.setParams(params);

            // 获取日志输出前缀
            String prefix = sysLog.prefix() + ":" + className + "." + methodName + "()";

            // 获取request
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest();

            // 设置IP地址
            logVO.setIp(ServletUtil.getClientIP(request, ""));
            logVO.setUserAgent(request.getHeader("user-agent"));

            // 用户名
            logVO.setUsername("test");
            // 执行方法前输出日志
            // 判断是否是方法之后输出日志，不是就输出参数日志
            if (!LogType.RESULT.equals(sysLog.value())) {
                log.info("＞＞＞＞{}：{}", prefix, params);
            }
            // 执行方法，并获取返回值
            result = point.proceed();

            // 执行方法后输出日志
            // 判断是否是方法之前输出日志，不是就输出参数日志
            if (!LogType.PARAMETER.equals(sysLog.value())) {
                log.info("＜＜＜＜ {}：{}", prefix, JSON.toJSON(result));
            }

            if (result instanceof Result) {
                Result r = (Result) result;
                logVO.setRetCode(String.valueOf(r.getCode()));
                logVO.setRetMsg(r.getMessage());
            } else {
                logVO.setRetCode("0");
                logVO.setRetMsg(JSON.toJSONString(result));
            }
        }catch (Exception e){
            if(e instanceof DataBaseException){
                logVO.setRetCode(String.valueOf(CodeType.DATABASE_ERROR.getCode()));
                logVO.setRetMsg(CodeType.DATABASE_ERROR.getMsg());
                log.error("＜＜＜＜ Execution method [{}] exception: {}", logVO.getMethod(), e.toString());
            }else{
                logVO.setRetCode(String.valueOf(CodeType.ERROR.getCode()));
                String msg = e.getMessage();
                if (null != e.getCause()) {
                    msg = e.getCause().getMessage();
                }
                logVO.setRetMsg(msg);
                log.error("＜＜＜＜ Execution method [{}] exception: {}", logVO.getMethod(), msg);
            }
            throw e;
        }finally {
            // 执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;
            logVO.setTime(time);
            logVO.setCreateTime(LocalDateTime.now());
            // 保存系统日志
            logService.saveLog(logVO);
        }

        return result;
    }
}
