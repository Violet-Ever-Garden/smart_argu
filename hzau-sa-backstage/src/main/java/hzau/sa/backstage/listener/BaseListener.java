package hzau.sa.backstage.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import hzau.sa.backstage.entity.BaseModel;
import hzau.sa.backstage.service.BaseService;

/**
 * @author wyh17
 * @version 1.0
 * @date 2020/8/28 15:55
 */
public class BaseListener extends AnalysisEventListener<BaseModel> {
    private BaseService baseService;
    public BaseListener(BaseService baseService){
        this.baseService=baseService;
    }


    @Override
    public void invoke(BaseModel data, AnalysisContext context) {
        if (baseService.isBaseExist(data.getBaseName())){
            return;
        }
        baseService.addBase(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
