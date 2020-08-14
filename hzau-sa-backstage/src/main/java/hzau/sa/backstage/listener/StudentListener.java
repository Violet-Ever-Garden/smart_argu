package hzau.sa.backstage.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import hzau.sa.backstage.dao.StudentDao;
import hzau.sa.backstage.entity.StudentVO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wyh17
 * @version 1.0
 * @date 2020/8/14 11:18
 */
public class StudentListener extends AnalysisEventListener<StudentVO> {
    @Autowired
    private StudentDao studentDao;

    @Override
    public void invoke(StudentVO data, AnalysisContext context) {
        studentDao.insert(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
