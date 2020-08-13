package hzau.sa.msg.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

<<<<<<< HEAD
//@Slf4j
//@Component
//public class MyMetaObjectHandler implements MetaObjectHandler {
//
//    //插入时填充策略
//    @Override
//    public void insertFill(MetaObject metaObject) {
//        log.info("start insert fill......");
//        //setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject)
//        this.setFieldValByName("createTime",new Date(),metaObject);
//    }
//
//    //更新时填充策略
//    @Override
//    public void updateFill(MetaObject metaObject) {
//        log.info("start insert fill......");
//        this.setFieldValByName("updateTime",new Date(),metaObject);
//    }
//}
=======
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    //插入时填充策略
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill......");
        //setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject)
        this.setFieldValByName("createTime", LocalDateTime.now(),metaObject);
    }

    //更新时填充策略
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start insert fill......");
        this.setFieldValByName("updateTime",new Date(),metaObject);
    }
}
>>>>>>> origin/haokai-dev
