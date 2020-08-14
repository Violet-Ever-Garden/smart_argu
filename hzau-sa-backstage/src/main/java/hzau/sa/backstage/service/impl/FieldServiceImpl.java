package hzau.sa.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
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
import hzau.sa.msg.enums.CodeType;
import hzau.sa.msg.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
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

    /**
     * 分页显示所有地块
     * @param pageNo 要显示的页数
     * @return
     */

    @Override
    public Result page(int pageNo){
        Page<FieldVO> page=new Page<>(pageNo,size);

        QueryWrapper<FieldVO> queryWrapper = new QueryWrapper<FieldVO>();

        IPage<FieldVO> iPage=fieldDao.selectPage(page,queryWrapper);

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("totalPages",iPage.getPages());
        hashMap.put("totalRecordNums",iPage.getTotal());
        hashMap.put("Records",iPage.getRecords());

        return ResultUtil.success(hashMap);
    }

    /**
     * 添加地块
     * @param fieldVO 要添加的地块
     * @return
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result addField(FieldVO fieldVO){
        if (fieldVO.getFieldName()==null){
            return ResultUtil.error("请输入地块名");
        }

        QueryWrapper<FieldVO> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("fieldName",fieldVO.getFieldName());

        FieldVO field = fieldDao.selectOne(queryWrapper);
        if (field!=null){
            return ResultUtil.error("地块名已存在，请重新输入");
        }else {
            fieldVO.setCreateTime(LocalDateTime.now());
        }

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
        queryWrapper.eq("fieldId",fieldId);

        FieldVO field = fieldDao.selectOne(queryWrapper);

        if (field==null){
            return ResultUtil.paramError("该地块id不存在");
        }

        if (fieldDao.deleteById(fieldId)!=0){
            return ResultUtil.success();
        }
        return ResultUtil.databaseError();

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
     * @param fieldVO 要更新的地块
     * @return
     */

    @Override
    public Result updateField(FieldVO fieldVO){
        if (fieldDao.updateById(fieldVO)!=0){
            return ResultUtil.success();
        }
        return ResultUtil.error("更新失败");
    }

    /**
     * 通过名字模糊查找地块
     * @param fieldName 查找地块名
     * @param pageNo 分页的页数
     * @return
     */
    @Override
    public Result findField(String fieldName,int pageNo){
        Page<FieldVO> page=new Page<>(pageNo,size);

        QueryWrapper<FieldVO> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("fieldname",fieldName);

        IPage<FieldVO> iPage = fieldDao.selectPage(page, queryWrapper);

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("totalPages",iPage.getPages());
        hashMap.put("totalRecordNums",iPage.getTotal());
        hashMap.put("Records",iPage.getRecords());

        return ResultUtil.success(hashMap);
    }
}
