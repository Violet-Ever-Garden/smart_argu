package hzau.sa.backstage.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.dao.BaseDao;
import hzau.sa.backstage.entity.Base;
import hzau.sa.backstage.entity.BaseModel;
import hzau.sa.backstage.entity.SchoolModel;
import hzau.sa.backstage.listener.BaseListener;
import hzau.sa.backstage.listener.SchoolListener;
import hzau.sa.backstage.service.BaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.msg.entity.BaseVO;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.FileUtil;
import hzau.sa.msg.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;

/**
 * <p>
 *  基地服务实现类
 * </p>
 *
 * @author lvhao
 * @since 2020-08-26
 */
@Service
public class BaseServiceImpl extends ServiceImpl<BaseDao, Base> implements BaseService {
    @Resource
    private BaseDao baseDao;
    private static final String TEMPLATE_PATH="/root/hzau/file/excelTemplate/baseTemplate.xlsx";
    /**
     * 增加基地
     * @param baseModel
     * @return
     */
    @Override
    public Result addBase(BaseModel baseModel){
        //判断重复性
        if(isBaseExist(baseModel.getBaseName())){
            return ResultUtil.error("基地名已存在");
        }
        Base base = new Base(baseModel);

        //获得学校id
        Integer schoolId=baseDao.querySchoolIdBySchoolName(baseModel.getSchoolName());
        if (schoolId==0){
            return ResultUtil.error("学校id获取失败");
        }

        //设置学校id
        base.setSchoolId(schoolId);

        //添加基地
        if(baseDao.insert(base)==0){
            return ResultUtil.error("添加基地失败");
        }

        return ResultUtil.success();
    }


    /**
     * 删除基地
     * @param baseId
     * @return
     */
    @Override
    public Result deleteBase(Integer baseId){
        if (baseDao.deleteById(baseId)==0){
            return ResultUtil.error("删除失败");
        }
        return ResultUtil.success();
    }

    /**
     * 批量删除基地
     * @param baseIds
     * @return
     */
    @Override
    public Result deleteBases(Integer[] baseIds){
        if (baseDao.deleteBatchIds(Arrays.asList(baseIds))==0){
            return ResultUtil.error("批量删除失败");
        }
        return ResultUtil.success();
    }


    /**
     * 更新基地
     * @param baseModel
     * @return
     */
    @Override
    public Result updateBase(BaseModel baseModel){
        //判断重复性
        if(isBaseExist(baseModel.getBaseName())){
            return ResultUtil.error("基地名已存在");
        }
        Base base = new Base(baseModel);

        //获得学校id
        Integer schoolId=baseDao.querySchoolIdBySchoolName(baseModel.getSchoolName());
        if (schoolId==0){
            return ResultUtil.error("学校id获取失败");
        }

        //设置学校id
        base.setSchoolId(schoolId);

        //添加基地
        if(baseDao.updateById(base)==0){
            return ResultUtil.error("更新基地失败");
        }

        return ResultUtil.success();
    }


    /**
     * 分页
     * @param page
     * @param baseName
     * @return
     */
    @Override
    public IPage<BaseModel> page(Page<BaseModel> page, String baseName){
        IPage<BaseModel> iPage = baseDao.page(page,"%"+baseName+"%");
        return iPage;
    }

    /**
     * 基地模板下载
     * @return
     */
    @Override
    public Result templateDownload(){
        String url= FileUtil.getFileUrl(BaseServiceImpl.TEMPLATE_PATH);
        return ResultUtil.success(url);
    }


    /**
     * 从基地模板添加文件
     * @param multipartFile
     * @param baseService
     * @return
     */
    @Override
    public Result addBaseByTemplate(MultipartFile multipartFile, BaseService baseService){
        String fileName=multipartFile.getOriginalFilename();
        String suffix=fileName.substring(fileName.lastIndexOf(".")+1);
        if (!suffix.equals("xlsx")){
            ResultUtil.error("文件必须为xlsx文件，文件模板请下载");
        }
        try {
            EasyExcel.read(multipartFile.getInputStream(),BaseModel.class,new BaseListener(baseService)).ignoreEmptyRow(true).doReadAll();
        } catch (IOException e) {
            e.printStackTrace();
            ResultUtil.error("文件导入失败");
        }
        return ResultUtil.success();
    }


    /**
     * 判断基地名字是否重复
     */
    @Override
    public boolean isBaseExist(String baseName){
        QueryWrapper<Base> baseQueryWrapper = new QueryWrapper<>();
        baseQueryWrapper.lambda().eq(Base::getBaseName,baseName);
        Base base = baseDao.selectOne(baseQueryWrapper);
        if(base==null){
            return false;
        }
        return true;
    }
}
