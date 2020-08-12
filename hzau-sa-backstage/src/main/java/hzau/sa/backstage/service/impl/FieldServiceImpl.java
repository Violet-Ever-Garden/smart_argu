package hzau.sa.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.UpdateChainWrapper;
import hzau.sa.backstage.dao.FieldDao;
import hzau.sa.backstage.entity.FieldVO;
import hzau.sa.backstage.service.FieldService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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

    private static final int size=10;

    @Override
    public Result page(int current){
        Page<FieldVO> page=new Page<>(current,size);
        QueryWrapper<FieldVO> queryWrapper = new QueryWrapper<FieldVO>();

        IPage<FieldVO> iPage=fieldDao.selectPage(page,queryWrapper);

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("totalPages",iPage.getPages());
        hashMap.put("totalRecordNums",iPage.getTotal());
        hashMap.put("Records",iPage.getRecords());

        return ResultUtil.success(hashMap);
    }

    @Override
    public Result addField(FieldVO fieldVO){
        if (fieldDao.insert(fieldVO)!=0){
            return ResultUtil.success();
        }
        return ResultUtil.error("插入失败");
    }

    @Override
    public Result deleteField(String fieldId){
        if (fieldDao.deleteById(fieldId)!=0){
            return ResultUtil.success();
        }
        return ResultUtil.error("删除失败");
    }

    @Override
    public Result updateField(FieldVO fieldVO){
        if (fieldDao.updateById(fieldVO)!=0){
            return ResultUtil.success();
        }
        return ResultUtil.error("更新失败");
    }

    @Override
    public Result findField(String fieldName){
        QueryWrapper<FieldVO> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("fieldname",fieldName);


        List<FieldVO> fieldVOList=fieldDao.selectList(queryWrapper);
        return ResultUtil.success(fieldVOList);
    }

}
