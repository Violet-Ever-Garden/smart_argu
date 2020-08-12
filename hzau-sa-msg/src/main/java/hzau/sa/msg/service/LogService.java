package hzau.sa.msg.service;

import hzau.sa.msg.dao.LogDao;
import hzau.sa.msg.entity.LogVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;

/**
 * @author LvHao
 * @Description :日志接口服务实现类
 * @date 2020-08-08 18:21
 */
@Slf4j
@Service
public class LogService extends BaseService<LogDao, LogVO> {

    @Resource
    private LogDao logDao;

    @Async
    @Transactional(rollbackFor = Exception.class)
    public void saveLog(final LogVO logVO) {
        try{
            logDao.insert(logVO);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.toString());
        }
    }
}
