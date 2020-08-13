package hzau.sa.backstage.dao;

import hzau.sa.backstage.BackStageApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LvHao
 * @Description :
 * @date 2020-08-12 16:54
 */
@SpringBootTest(classes = BackStageApplication.class)
class AsClassmonitorDaoTest {

    @Resource
    private AsClassmonitorDao classmonitorDao;

    @Test
    void queryMonitorIdsByClassId() {
        int id = 1;

        List<String> integers = classmonitorDao.queryMonitorIdsByClassId(1);

        for(String ids : integers){
            System.out.println(ids);
        }
    }
}