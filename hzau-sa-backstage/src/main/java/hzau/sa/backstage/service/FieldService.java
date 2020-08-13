package hzau.sa.backstage.service;

import hzau.sa.msg.entity.Result;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.backstage.dao.FieldDao;
import hzau.sa.backstage.entity.FieldVO;

/**
 * field 服务实现接口
 * @author wuyihu
 * @date 2020-08-12
 */
public interface FieldService  {
    public Result page(int pageNo);
    public Result addField(FieldVO fieldVO);
    public Result deleteField(String fieldId);
    public Result updateField(FieldVO fieldVO);
    public Result findField(String fieldName,int pageNo);
}