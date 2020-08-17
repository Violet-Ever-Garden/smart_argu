package hzau.sa.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.MeasureVO;
import hzau.sa.backstage.dao.MeasureDao;
import hzau.sa.backstage.entity.MeasureVO;
import hzau.sa.backstage.entity.MeasureWrapper;
import hzau.sa.backstage.service.MeasureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.apache.poi.hssf.record.DVALRecord;
import org.apache.xmlbeans.impl.jam.visitor.MVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
        measureVO.setCreateUser(measureVO.getCurrentUserName());
        measureVO.setLastModifiedUser(measureVO.getCreateUser());

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
        measureVO.setLastModifiedUser(measureVO.getCurrentUserName());

//        QueryWrapper<MeasureVO> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("measureId",measureVO.getMeasureId());
//        measureVO.setCreateUser(measureDao.selectOne(queryWrapper).getCreateUser());

        if (measureDao.updateById(measureVO)==0){
            return ResultUtil.error("更新失败");
        }
        return ResultUtil.success();

    }

}
