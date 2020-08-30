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
 * @date 2020-08-12 16:39
 */
@SpringBootTest(classes = BackStageApplication.class)
class AsClassfieldDaoTest {

    @Resource
    private AsClassfieldDao asClassfieldDao;


    //@Test
    //void queryFieldIdsByClassId() {
    //    int id = 1;
    //    List<String> integerList = asClassfieldDao.queryFieldIdsByClassId(id);
//
    //    for(String ids : integerList){
    //        System.out.println(ids);
    //    }
    //}

    @Test
    void queryFieldIdByName() {
        System.out.println(asClassfieldDao.queryFieldIdByName("测试田1"));
    }
}