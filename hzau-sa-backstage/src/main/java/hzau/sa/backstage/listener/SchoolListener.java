package hzau.sa.backstage.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import hzau.sa.backstage.entity.SchoolModel;
import hzau.sa.backstage.entity.SchoolVO;
import hzau.sa.backstage.service.SchoolService;
import hzau.sa.backstage.service.impl.SchoolServiceImpl;
import org.apache.poi.ss.util.SSCellRange;

/**
 * @author wyh17
 * @version 1.0
 * @date 2020/8/28 9:19
 */
public class SchoolListener extends AnalysisEventListener<SchoolModel> {
    private SchoolServiceImpl schoolService;

    public SchoolListener(SchoolServiceImpl schoolService){
        this.schoolService=schoolService;
    }

    @Override
    public void invoke(SchoolModel data, AnalysisContext context) {
        if(schoolService.isSchoolExist(data.getSchoolName())){
            return;
        }
        schoolService.addSchool(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
