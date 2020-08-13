package hzau.sa.backstage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.BackStageApplication;
import hzau.sa.backstage.entity.ClassManage;
import hzau.sa.backstage.entity.ClassVO;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LvHao
 * @Description :
 * @date 2020-08-12 18:07
 */
@SpringBootTest(classes = BackStageApplication.class)
class ClassServiceTest {

    @Resource
    private ClassService classService;

    @Ignore
    @Test
    void findAllClass() {
        ClassVO classVO = new ClassVO();
        classVO.setClassId(1);

        List<ClassManage> list = (List<ClassManage>) classService.findClass(new Page(),null);

        for(ClassManage classManage : list){
            System.out.println(classManage.getClassName() +
                    "  " + classManage.getClassGrade() +
                    "  " + classManage.getClassFields() +
                    "  " + classManage.getClassMonitor());
        }
    }
}