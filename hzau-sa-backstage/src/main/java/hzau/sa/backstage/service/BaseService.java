package hzau.sa.backstage.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.BaseModel;
import hzau.sa.msg.entity.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * base 服务实现类
 * @author wyh
 * @date 2020-08-26
 */
public interface BaseService  {
    public Result addBase(BaseModel baseModel);
    public Result deleteBase(Integer baseId);
    public Result deleteBases(Integer[] baseIds);
    public Result updateBase(BaseModel baseModel);
    public IPage<BaseModel> page(Page<BaseModel> page, String baseName);
    public Result templateDownload();
    public Result addBaseByTemplate(MultipartFile multipartFile,BaseService baseService);
    public boolean isBaseExist(String baseName);
}