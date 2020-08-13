package hzau.sa.backstage.service;

import hzau.sa.msg.entity.Result;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.backstage.dao.MeasureDao;
import hzau.sa.backstage.entity.MeasureVO;

/**
 * measure 服务实现接口
 * @author wuyihu
 * @date 2020-08-12
 */
public interface MeasureService  {
    public Result addMeasure(MeasureVO measureVO);
    public Result deleteMeasure(Integer measureId);
    public Result deleteMeasures(Integer[] measureIds);
    public Result updateMesure(MeasureVO measureVO);
    public Result findMeasure(String measureName,int pageNo);
    public Result page(int pageNo);
}