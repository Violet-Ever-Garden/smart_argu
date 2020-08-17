package hzau.sa.backstage.service;

import hzau.sa.backstage.entity.FieldWrapper;
import hzau.sa.msg.entity.Result;
import org.omg.PortableInterceptor.INACTIVE;
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
    public Result addField(FieldWrapper fieldWrapper);
    public Result deleteField(Integer fieldId);
    public Result updateField(FieldWrapper fieldWrapper);
    public Result deleteFields(Integer[] fieldIds);
}