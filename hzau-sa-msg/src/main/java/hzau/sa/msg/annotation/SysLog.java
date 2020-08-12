package hzau.sa.msg.annotation;

import hzau.sa.msg.enums.LogType;

import java.lang.annotation.*;

/**
 * @author LvHao
 * @Description : 切面的注解
 * @date 2020-08-08 17:55
 */
@Inherited
@Documented
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface SysLog {

    /**
     * 打印日志类型，{@link LogTypeEnum}
     *
     * @return
     */
    LogType value() default LogType.ALL;

    /**
     * 日志输出前缀（建议配置接口名称）
     *
     * @return
     */
    String prefix() default "";

}
