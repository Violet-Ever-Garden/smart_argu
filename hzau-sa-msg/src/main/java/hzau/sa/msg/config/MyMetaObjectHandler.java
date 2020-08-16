package hzau.sa.msg.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import hzau.sa.msg.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    private final String createTime = "createTime";
    private final String createUser = "createUser";
    private final String updateTime = "lastModifiedTime";
    private final String updateUser = "lastModifiedUser";

    @Override
    public void insertFill(MetaObject metaObject) {

        if (null == getFieldValByName(createTime,metaObject)) {
            setInsertFieldValByName(createTime, LocalDateTime.now(), metaObject);
        }
        if (null == getFieldValByName(createUser,metaObject)) {
            setInsertFieldValByName(createUser, JwtUtils.currentUser(), metaObject);
        }
        if (null == getFieldValByName(updateTime,metaObject)) {
            setInsertFieldValByName(updateTime, LocalDateTime.now(), metaObject);
        }
        if (null == getFieldValByName(updateUser,metaObject)) {
            setInsertFieldValByName(updateUser, JwtUtils.currentUser(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (null == getFieldValByName(updateTime,metaObject)) {
            setUpdateFieldValByName(updateTime, LocalDateTime.now(), metaObject);
        }
        if (null == getFieldValByName(updateUser,metaObject)) {
            setUpdateFieldValByName(updateUser, JwtUtils.currentUser(), metaObject);
        }
    }
}
