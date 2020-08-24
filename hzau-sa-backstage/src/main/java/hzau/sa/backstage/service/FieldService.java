package hzau.sa.backstage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.FieldModel;
import hzau.sa.backstage.entity.FieldWrapper;
import hzau.sa.msg.entity.Result;

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
    public IPage<FieldModel> page(Page<FieldModel> page, String fieldName);
}