package hzau.sa.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.backstage.dao.MeasureDao;
import hzau.sa.backstage.entity.MeasureVO;
import hzau.sa.backstage.entity.MeasureWrapper;
import hzau.sa.backstage.service.MeasureService;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuyihu
 * @since 2020-08-12
 */
@Service
public class MeasureServiceImpl extends ServiceImpl<MeasureDao, MeasureVO> implements MeasureService {

    @Autowired
    private MeasureDao measureDao;

    /**
     * 增加措施
     * @param measureWrapper 要增加的措施
     * @return
     */
    @Override
    public Result addMeasure(MeasureWrapper measureWrapper){
        MeasureVO measureVO = new MeasureVO(measureWrapper);
        if (measureVO.getMeasureName()==null){
            return ResultUtil.paramError("措施名不能为空");
        }

        //判断名字重复性
        QueryWrapper<MeasureVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MeasureVO::getMeasureName,measureVO.getMeasureName());
        MeasureVO measureVOSelect = measureDao.selectOne(queryWrapper);
        if (measureVOSelect!=null){
            return ResultUtil.error("措施名字已存在");
        }

        //插入
        if (measureDao.insert(measureVO)==0){
            return ResultUtil.error("插入失败");
        }
        return ResultUtil.success();
    }

    /**
     * 删除措施
     * @param measureId 删除措施的id
     * @return
     */
    @Override
    public Result deleteMeasure(Integer measureId) {
        QueryWrapper<MeasureVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MeasureVO::getMeasureId,measureId);

        MeasureVO measure = measureDao.selectOne(queryWrapper);

        if (measure==null){
           return ResultUtil.paramError("想要删除的措施id不存在");
        }

        if (measureDao.deleteById(measureId)==0){
            return ResultUtil.databaseError();
        }
        return ResultUtil.success();
    }

    /**
     * 批量删除措施
     * @param measureIds 批量删除措施id
     * @return
     */
    @Override
    public Result deleteMeasures(Integer[] measureIds){
        if (measureDao.deleteBatchIds(Arrays.asList(measureIds))!=0){
            return ResultUtil.success();
        }
        return ResultUtil.error("批量删除失败");
    }

    /**
     * 更新措施
     * @param measureWrapper 更新的措施
     * @return
     */
    @Override
    public Result updateMesure(MeasureWrapper measureWrapper){
        MeasureVO measureVO = new MeasureVO(measureWrapper);
        if (measureWrapper.getMeasureName()==null){
            return ResultUtil.paramError("措施名不能为空");
        }

        //判断名字重复性
        QueryWrapper<MeasureVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MeasureVO::getMeasureId,measureVO.getMeasureId());
        MeasureVO measureVOSelect = measureDao.selectOne(queryWrapper);
        if (isMeasureExist(measureVO.getMeasureName()) && (!measureVO.getMeasureName().equals(measureVOSelect.getMeasureName()))){
            return ResultUtil.error("措施名字已存在");
        }

        //更新
        if (measureDao.updateById(measureVO)==0){
            return ResultUtil.error("更新失败");
        }
        return ResultUtil.success();

    }

    public boolean isMeasureExist(String measureName){
        QueryWrapper<MeasureVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MeasureVO::getMeasureName,measureName);
        MeasureVO measureVO = measureDao.selectOne(queryWrapper);
        if (measureVO==null){
            return false;
        }
        return true;
    }

}
