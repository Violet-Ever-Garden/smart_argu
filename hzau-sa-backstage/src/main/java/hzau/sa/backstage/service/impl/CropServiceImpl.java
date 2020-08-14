package hzau.sa.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import hzau.sa.backstage.dao.CropDao;
import hzau.sa.backstage.dao.CropParameterDao;
import hzau.sa.backstage.entity.CropParameterVO;
import hzau.sa.backstage.entity.CropVO;
import hzau.sa.backstage.service.CropService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author haokai
 * @since 2020-08-12
 */
@Service
public class CropServiceImpl extends ServiceImpl<CropDao, CropVO> implements CropService {
    @Autowired
    CropDao cropDao;
    @Autowired
    CropParameterDao cropParameterDao;

    @Transactional(rollbackFor = Exception.class)
    public Result insert(CropVO cropVO) {

        HashMap<String, Object> query = new HashMap<>();
        query.put("cropName",cropVO.getCropName());

        QueryWrapper<CropVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cropName",cropVO.getCropName());

        CropVO cropE =cropDao.selectOne(queryWrapper);
        if(null!=cropE){
            return ResultUtil.error("该作物已存在");
        }else {
            cropVO.setCreateTime(LocalDateTime.now());
            int insert = cropDao.insert(cropVO);
            if (0==insert){
                return ResultUtil.databaseError();
            }else{
                HashMap hashMap = new HashMap<String,Object>();
                hashMap.put("cropName",cropVO.getCropName());
                cropVO = (CropVO) cropDao.selectOne(queryWrapper);
                //添加属性参数
                CropParameterVO porperties = new CropParameterVO();
                porperties.setCropId(cropVO.getCropId());
                porperties.setSortNumber(1);
                porperties.setParameterName("属性");
                cropParameterDao.insert(porperties);
                //添加检测时间参数
                CropParameterVO testTime = new CropParameterVO();
                testTime.setCropId(cropVO.getCropId());
                testTime.setSortNumber(2);
                testTime.setParameterName("检测时间");
                cropParameterDao.insert(testTime);
                //添加生育期参数
                CropParameterVO growthPeriod = new CropParameterVO();
                growthPeriod.setCropId(cropVO.getCropId());
                growthPeriod.setSortNumber(3);
                growthPeriod.setParameterName("生育期");
                cropParameterDao.insert(growthPeriod);
                //添加处理参数
                CropParameterVO process = new CropParameterVO();
                process.setCropId(cropVO.getCropId());
                process.setSortNumber(4);
                process.setParameterName("处理");
                cropParameterDao.insert(process);


                return ResultUtil.success("成功增加");
            }
        }
    }



    public Result delete(int cropId) {
        boolean b = removeById(cropId);
        if(true==b){
            return ResultUtil.success("成功删除");
        }else {
            return ResultUtil.databaseError();
        }
    }


}
