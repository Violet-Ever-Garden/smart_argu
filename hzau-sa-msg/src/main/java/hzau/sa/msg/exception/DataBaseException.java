package hzau.sa.msg.exception;

import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;

/**
 * @author LvHao
 * @Description : 数据库异常处理
 * @date 2020-08-09 11:03
 */
public class DataBaseException extends Exception{

    /**
     * 数据插入异常
     * @param object 异常的具体位置
     * @return
     */
    public Result<Object> insertError(Object object){
        return ResultUtil.databaseError(object);
    }
}
