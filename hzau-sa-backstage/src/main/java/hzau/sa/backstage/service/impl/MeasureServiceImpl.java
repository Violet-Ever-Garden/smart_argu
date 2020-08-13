package hzau.sa.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.MeasureVO;
import hzau.sa.backstage.dao.MeasureDao;
import hzau.sa.backstage.entity.MeasureVO;
import hzau.sa.backstage.service.MeasureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private static final int size=10;

    @Autowired
    private MeasureDao measureDao;

    /**
     * 增加措施
     * @param measureVO 要增加的措施
     * @return
     */
    @Override
    public Result addMeasure(MeasureVO measureVO){
        if (measureDao.insert(measureVO)!=0){
            return ResultUtil.success();
        }
        return ResultUtil.error("插入失败");
    }

    /**
     * 删除措施
     * @param measureId 删除措施的id
     * @return
     */

    @Override
    public Result deleteMeasure(Integer measureId) {
        if (measureDao.deleteById(measureId)!=0){
            return ResultUtil.success();
        }
        return ResultUtil.error("删除失败");
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
     * @param measureVO 更新的措施
     * @return
     */
    @Override
    public Result updateMesure(MeasureVO measureVO){
        if (measureDao.updateById(measureVO)!=0){
            return ResultUtil.success();
        }
        return ResultUtil.error("更新失败");

    }

    /**
     * 模糊查找措施
     * @param measureName 模糊查找措施名字
     * @param pageNo 要显示的页数
     * @return
     */

    @Override
    public Result findMeasure(String measureName,int pageNo){
        Page<MeasureVO> page = new Page<MeasureVO>(pageNo,size);

        QueryWrapper<MeasureVO> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("mesureName",measureName);

        IPage<MeasureVO> iPage= measureDao.selectPage(page, queryWrapper);

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("totalPages",iPage.getPages());
        hashMap.put("totalRecordNums",iPage.getTotal());
        hashMap.put("Records",iPage.getRecords());

        return ResultUtil.success(hashMap);

    }

    /**
     * 分页显示
     * @param pageNo 要显示的页数
     * @return
     */
    @Override
    public Result page(int pageNo){
        Page<MeasureVO> page=new Page<>(size,pageNo);
        QueryWrapper<MeasureVO> queryWrapper=new QueryWrapper<>();

        IPage<MeasureVO> iPage= measureDao.selectPage(page, queryWrapper);

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("totalPages",iPage.getPages());
        hashMap.put("totalRecordNums",iPage.getTotal());
        hashMap.put("Records",iPage.getRecords());

        return ResultUtil.success(hashMap);
    }
}
