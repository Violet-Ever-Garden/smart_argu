package hzau.sa.backstage.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import hzau.sa.backstage.dao.StudentDao;
import hzau.sa.backstage.entity.StudentVO;
import hzau.sa.backstage.entity.StudentWrapper;
import hzau.sa.backstage.service.StudentService;
import hzau.sa.backstage.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author wyh17
 * @version 1.0
 * @date 2020/8/14 11:18
 */
public class StudentListener extends AnalysisEventListener<StudentWrapper> {
    public StudentServiceImpl  studentService;

    public StudentListener(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @Override
    public void invoke(StudentWrapper data, AnalysisContext context) {
        studentService.addStudent(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
