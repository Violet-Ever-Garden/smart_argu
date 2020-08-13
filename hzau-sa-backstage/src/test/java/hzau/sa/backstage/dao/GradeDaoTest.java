package hzau.sa.backstage.dao;

import hzau.sa.backstage.BackStageApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LvHao
 * @Description :
 * @date 2020-08-12 16:23
 */
@SpringBootTest(classes = BackStageApplication.class)
class GradeDaoTest {

    @Resource
    private GradeDao gradeDao;


    @Test
    void selectGradeNameById() {
        int id = 1;
        System.out.println(gradeDao.selectGradeNameById(id));
    }

    @Test
    void selectGradeIdByName() {
        System.out.println(gradeDao.selectGradeIdByName("2018级"));
    }
}