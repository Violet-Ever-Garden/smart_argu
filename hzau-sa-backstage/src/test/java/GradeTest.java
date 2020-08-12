import cn.hutool.core.date.DateTime;
import hzau.sa.backstage.BackStageApplication;
import hzau.sa.backstage.dao.GradeDao;
import hzau.sa.backstage.entity.GradeVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootTest(classes = BackStageApplication.class)
public class GradeTest {
    @Autowired
    GradeDao gradeDao;
    @Test
    void insertGrade(){
        //for (int i = 1; i < 5; i++) {
            GradeVO gradeVO = new GradeVO();
            gradeVO.setGradeName("201");
            gradeVO.setOperateTime(LocalDateTime.now());
            gradeDao.insert(gradeVO);
        //}
    }
}
