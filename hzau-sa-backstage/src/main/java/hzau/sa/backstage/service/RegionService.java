package hzau.sa.backstage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.RegionModel;
import hzau.sa.msg.entity.Result;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.backstage.dao.RegionDao;
import hzau.sa.backstage.entity.RegionVO;

/**
 * region 服务实现类
 * @author wyh
 * @date 2020-08-28
 */
public interface RegionService  {
    public Result addRegion(RegionModel regionModel);
    public Result deleteRegion(Integer regionId);
    public Result deleteRegions(Integer[] regionIds);
    public Result updateRegion(RegionModel regionModel);
    public IPage<RegionModel> page(Page<RegionModel> page,String regionName);
    public boolean isRegionExist(String regionName);
}