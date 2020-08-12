package hzau.sa.msg.service;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Map;

/**
 * @author LvHao
 * @Description :用户接口服务实现类
 * @date 2020-08-08 18:19
 */
public class BaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    public IPage<T> page(IPage<T> page, Map<String, Object> columnMap) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(
                Convert.convert(page.getClass().getGenericSuperclass(), columnMap));
        return this.baseMapper.selectPage(page, queryWrapper);
    }

}

