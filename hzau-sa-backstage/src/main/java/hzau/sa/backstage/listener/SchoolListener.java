package hzau.sa.backstage.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import hzau.sa.backstage.entity.SchoolModel;
import hzau.sa.backstage.service.SchoolService;

/**
 * @author wyh17
 * @version 1.0
 * @date 2020/8/28 9:19
 */
public class SchoolListener extends AnalysisEventListener<SchoolModel> {
    private SchoolService schoolService;

    @Override
    public void invoke(SchoolModel data, AnalysisContext context) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
