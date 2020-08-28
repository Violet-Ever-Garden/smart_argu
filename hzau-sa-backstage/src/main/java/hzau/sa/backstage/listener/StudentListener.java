package hzau.sa.backstage.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import hzau.sa.backstage.entity.StudentWrapper;
import hzau.sa.backstage.service.impl.StudentServiceImpl;

/**
 * @author wyh17
 * @version 1.0
 * @date 2020/8/14 11:18
 */
public class StudentListener extends AnalysisEventListener<StudentWrapper> {
    private StudentServiceImpl  studentService;

    public StudentListener(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @Override
    public void invoke(StudentWrapper data, AnalysisContext context) {
        if (data.getIsOperatemonitor().equals("是") && data.getIsOperatemonitor().equals("否")){
            return;
        }

        if (data.getIsOperatewfm().equals("是") && data.getIsOperatewfm().equals("否")){
            return;
        }
        studentService.addStudent(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
