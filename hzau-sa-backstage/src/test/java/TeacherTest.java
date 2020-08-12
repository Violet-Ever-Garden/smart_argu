import hzau.sa.backstage.BackStageApplication;
import hzau.sa.backstage.dao.TeacherDao;
import hzau.sa.backstage.entity.TeacherVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BackStageApplication.class)
public class TeacherTest {
    @Autowired
    TeacherDao teacherDao;
    @Test
    void insert(){
        TeacherVO teacherVO = new TeacherVO();
        teacherVO.setType("teacher");
        teacherVO.setTeacherName("xxx");
        teacherVO.setTeacherId("111222444");
        teacherVO.setPhoneNumber("12312312313");

        teacherVO.setPassword("123456");
        teacherVO.setIsOperatemonitor(0);
        teacherDao.insert(teacherVO);
    }
}
