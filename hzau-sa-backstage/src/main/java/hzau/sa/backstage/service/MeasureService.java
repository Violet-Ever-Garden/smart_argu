package hzau.sa.backstage.service;

import hzau.sa.backstage.entity.MeasureWrapper;
import hzau.sa.msg.entity.Result;

/**
 * measure 服务实现接口
 * @author wuyihu
 * @date 2020-08-12
 */
public interface MeasureService  {
    public Result addMeasure(MeasureWrapper measureWrapper);
    public Result deleteMeasure(Integer measureId);
    public Result deleteMeasures(Integer[] measureIds);
    public Result updateMesure(MeasureWrapper measureWrapper);
}