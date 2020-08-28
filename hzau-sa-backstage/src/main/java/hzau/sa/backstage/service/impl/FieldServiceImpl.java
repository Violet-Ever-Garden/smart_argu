package hzau.sa.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.backstage.dao.FieldDao;
import hzau.sa.backstage.dao.RegionDao;
import hzau.sa.backstage.entity.FieldModel;
import hzau.sa.backstage.entity.FieldVO;
import hzau.sa.backstage.entity.FieldWrapper;
import hzau.sa.backstage.entity.RegionVO;
import hzau.sa.backstage.service.FieldService;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * <p>
 *  服务实现类
 *  基本的增删改查
 * </p>
 *
 * @author wuyihu
 * @since 2020-08-12
 */
@Service
public class FieldServiceImpl extends ServiceImpl<FieldDao, FieldVO> implements FieldService {
    @Autowired
    private FieldDao fieldDao;

    @Autowired
    private RegionDao regionDao;

    /**
     * 添加地块
     * @param fieldWrapper 要添加的地块
     * @return
     */
    @Override
    public Result addField(FieldWrapper fieldWrapper){
        //解包装
        FieldVO fieldVO = new FieldVO(fieldWrapper);

        //判断存在性
        if (fieldWrapper.getFieldName()==null || fieldWrapper.getRegionName()==null){
            return ResultUtil.error("请输入地块名或区域名");
        }

        //判断区域正确性以及转换
        QueryWrapper<RegionVO> regionVOQueryWrapper = new QueryWrapper<>();
        regionVOQueryWrapper.lambda().eq(RegionVO::getRegionName,fieldWrapper.getRegionName());
        RegionVO regionVO = regionDao.selectOne(regionVOQueryWrapper);

        if (regionVO==null){
            return ResultUtil.error("区域名不存在");
        }else {
            fieldVO.setRegionId(regionVO.getRegionId());
        }


        //判断地块姓名重复性
        QueryWrapper<FieldVO> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(FieldVO::getFieldName,fieldWrapper.getFieldName());

        FieldVO field = fieldDao.selectOne(queryWrapper);
        if (field!=null){
            return ResultUtil.error("地块名已存在，请重新输入");
        }

        //插入
        if (fieldDao.insert(fieldVO)==0){
            return ResultUtil.error("新增地块失败");
        }
        return ResultUtil.success("地块增加成功");
    }


    /**
     * 删除地块
     * @param fieldId 删除地块id
     * @return
     */
    @Override
    public Result deleteField(Integer fieldId){
        QueryWrapper<FieldVO> queryWrapper = new QueryWrapper<FieldVO>();
        queryWrapper.lambda().eq(FieldVO::getFieldId,fieldId);

        FieldVO field = fieldDao.selectOne(queryWrapper);

        if (field==null){
            return ResultUtil.paramError("该地块id不存在");
        }

        if (fieldDao.deleteById(fieldId)==0){
            return ResultUtil.databaseError();
        }
        return ResultUtil.success();
    }


    /**
     * 批量删除地块
     * @param fieldIds 批量删除的ids
     * @return
     */
    @Override
    public Result deleteFields(Integer[] fieldIds){
        if (fieldDao.deleteBatchIds(Arrays.asList(fieldIds))!=0){
            return ResultUtil.success();
        }
        return ResultUtil.error("批量删除失败");
    }


    /**
     * 更新地块
     * @param fieldWrapper 要更新的地块
     * @return
     */
    @Override
    public Result updateField(FieldWrapper fieldWrapper){
        //解包装
        FieldVO fieldVO = new FieldVO(fieldWrapper);

        //判断存在性
        if (fieldWrapper.getFieldName()==null ||fieldWrapper.getRegionName()==null){
            return ResultUtil.error("地块名和区域名不能为空");
        }

        //判断区域正确性及转换
        QueryWrapper<RegionVO> regionVOQueryWrapper = new QueryWrapper<>();
        regionVOQueryWrapper.lambda().eq(RegionVO::getRegionName,fieldWrapper.getRegionName());
        RegionVO regionVO = regionDao.selectOne(regionVOQueryWrapper);

        if (regionVO==null){
            return ResultUtil.error("区域名不存在");
        }else {
            fieldVO.setRegionId(regionVO.getRegionId());
        }

        //判断地块姓名重复性
        QueryWrapper<FieldVO> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(FieldVO::getFieldName,fieldWrapper.getFieldName());

        FieldVO field = fieldDao.selectOne(queryWrapper);
        if (field!=null){
            return ResultUtil.error("地块名已存在，请重新输入");
        }


        if (fieldDao.updateById(fieldVO)==0){
            return ResultUtil.error("更新失败");
        }
        return ResultUtil.success();
    }

    @Override
    public IPage<FieldModel> page(Page<FieldModel> page, String fieldName){
        IPage<FieldModel> iPage =fieldDao.page(page,"%"+fieldName+"%");
        return iPage;
    }
}
