package hzau.sa.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.dao.RegionDao;
import hzau.sa.backstage.entity.RegionModel;
import hzau.sa.backstage.entity.RegionVO;
import hzau.sa.backstage.service.RegionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * @since 2020-08-28
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionDao, RegionVO> implements RegionService {
    @Autowired
    private RegionDao regionDao;

    /**
     * 添加区域
     * @param regionModel
     * @return
     */
    @Override
    public Result addRegion(RegionModel regionModel){
        //判断区域名是否存在
        if (isRegionExist(regionModel.getRegionName())){
            return ResultUtil.error("区域名已存在");
        }

        //解包装
        RegionVO regionVO = new RegionVO(regionModel);

        //填充学校和基地
        regionVO.setSchoolId(regionDao.querySchoolIdByBaseName(regionModel.getBaseName()));
        regionVO.setBaseId(regionDao.queryBaseIdByBaseName(regionModel.getBaseName()));

        //插入
        if (regionDao.insert(regionVO)==0){
            return ResultUtil.error("增加区域失败");
        }

        return ResultUtil.success();
    }

    /**
     * 删除区域
     * @param regionId
     * @return
     */
    @Override
    public Result deleteRegion(Integer regionId){
        if (regionDao.deleteById(regionId)==0){
            return ResultUtil.error("删除失败");
        }
        return ResultUtil.success();
    }

    /**
     * 批量删除区域
     * @param regionIds
     * @return
     */
    @Override
    public Result deleteRegions(Integer[] regionIds){
        if (regionDao.deleteBatchIds(Arrays.asList(regionIds))==0){
            return ResultUtil.error("批量删除失败");
        }
        return ResultUtil.success();
    }

    /**
     * 更新区域
     * @param regionModel
     * @return
     */
    @Override
    public Result updateRegion(RegionModel regionModel){
        QueryWrapper<RegionVO> regionVOQueryWrapper = new QueryWrapper<>();
        regionVOQueryWrapper.lambda().eq(RegionVO::getRegionId,regionModel.getRegionId());
        RegionVO regionVOSelect = regionDao.selectOne(regionVOQueryWrapper);

        //判断区域名是否存在
        if (isRegionExist(regionModel.getRegionName()) && (!regionModel.getRegionName().equals(regionVOSelect.getRegionName()))){
            return ResultUtil.error("区域名已存在");
        }

        //解包装
        RegionVO regionVO = new RegionVO(regionModel);

        //填充学校和基地
        regionVO.setSchoolId(regionDao.querySchoolIdByBaseName(regionModel.getBaseName()));
        regionVO.setBaseId(regionDao.queryBaseIdByBaseName(regionModel.getBaseName()));

        //更新
        if (regionDao.updateById(regionVO)==0){
            return ResultUtil.error("增加更新失败");
        }

        return ResultUtil.success();
    }

    /**
     * 分页
     * @param page
     * @param regionName
     * @return
     */
    @Override
    public IPage<RegionModel> page(Page<RegionModel> page, String regionName){
        IPage<RegionModel> iPage = regionDao.page(page, "%" + regionName + "%");
        return iPage;
    }

    /**
     * 判断区域名是否存在
     * @param regionName
     * @return
     */
    @Override
    public boolean isRegionExist(String regionName){
        QueryWrapper<RegionVO> regionVOQueryWrapper = new QueryWrapper<>();
        regionVOQueryWrapper.lambda().eq(RegionVO::getRegionName,regionName);
        RegionVO regionVO = regionDao.selectOne(regionVOQueryWrapper);
        if (regionVO==null){
            return false;
        }
        return true;
    }
}
