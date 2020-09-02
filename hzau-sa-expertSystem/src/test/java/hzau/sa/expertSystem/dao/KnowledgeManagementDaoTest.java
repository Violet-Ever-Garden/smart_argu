package hzau.sa.expertSystem.dao;

import hzau.sa.expertSystem.ExpertSystemApplication;
import hzau.sa.expertSystem.entity.KnowledgeManagementView;
import hzau.sa.msg.enums.FileEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


/**
 * @author wyh17
 * @version 1.0
 * @date 2020/9/2 16:58
 */
@SpringBootTest(classes = ExpertSystemApplication.class)
class KnowledgeManagementDaoTest {
//    @Autowired
//    private KnowledgeManagementDao knowledgeManagementDao;
//
//    @Test
//    void queryKnowledgeById() {
//        KnowledgeManagementView knowledgeManagementView = knowledgeManagementDao.queryKnowledgeById(23, String.valueOf(FileEnum.KNOWLEDGE));
//        String s = knowledgeManagementView.toString();
//        System.out.println(s);
//    }
}