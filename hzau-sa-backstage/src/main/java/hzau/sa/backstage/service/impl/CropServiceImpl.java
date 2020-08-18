package hzau.sa.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import hzau.sa.backstage.dao.CropDao;
import hzau.sa.backstage.dao.CropParameterDao;
import hzau.sa.backstage.entity.CropModel;
import hzau.sa.backstage.entity.CropParameterVO;
import hzau.sa.backstage.entity.CropVO;
import hzau.sa.backstage.service.CropService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.msg.dao.FileDao;
import hzau.sa.msg.entity.FileVO;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.FileEnum;
import hzau.sa.msg.exception.DataBaseException;
import hzau.sa.msg.util.FileUtil;
import hzau.sa.msg.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    FileDao fileDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result insert(String cropName ,MultipartFile picture)  {
        CropVO cropVO = new CropVO();
        cropVO.setCropName(cropName);
        QueryWrapper<CropVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CropVO::getCropName,cropVO.getCropName());

        CropVO cropE =cropDao.selectOne(queryWrapper);
        if(null!=cropE){
            return ResultUtil.error("该作物已存在");
        }else {
            int insert = cropDao.insert(cropVO);
            if (0==insert){
                return ResultUtil.databaseError();
            }else{

                //添加属性参数
                CropParameterVO properties = new CropParameterVO();
                properties.setCropId(cropVO.getCropId());
                properties.setSortNumber(1);
                properties.setParameterName("属性");
                cropParameterDao.insert(properties);
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


                if(picture!=null){
                    String absolutePath = "";
                    try{
                        //图片插入
                        //获取图片存储的绝对路径
                        absolutePath = FileUtil.uploadFile(FileEnum.CROP, cropName, picture);
                    }catch (IOException e){
                        e.printStackTrace();
                        return new DataBaseException().insertError(e);
                    }

                    //获取图片的映射路径
                    String url = FileUtil.getFileUrl(absolutePath);
                    //添加图片路径
                    FileVO fileVO = new FileVO();
                    fileVO.setConnectId(String.valueOf(cropVO.getCropId()));
                    fileVO.setUrl(url);
                    fileVO.setFileAbsolutePath(absolutePath);
                    fileVO.setFileType(String.valueOf(FileEnum.CROP));
                    int insertPicture = fileDao.insert(fileVO);
                    if(0==insertPicture){
                        return ResultUtil.databaseError();
                    }
                }

                return ResultUtil.success("成功增加");
            }
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result delete(int cropId) {
        QueryWrapper<FileVO> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(FileVO::getConnectId,String.valueOf(cropId))
                .eq(FileVO::getFileType,String.valueOf(FileEnum.CROP));
        FileVO fileVO = fileDao.selectOne(queryWrapper);
        if(fileVO!=null){
            boolean deleteFile = FileUtil.deleteFile(fileVO.getFileAbsolutePath());
            int i = fileDao.deleteById(fileVO.getFileId());
        }


        boolean b = removeById(cropId);

        if(true==b){
            return ResultUtil.success("成功删除");
        }else {
            return ResultUtil.databaseError();
        }
    }


    public boolean removeByIdsAndPicture(List<Integer> ids) {
        boolean b = removeByIds(ids);
        for (int i : ids) {
            QueryWrapper<FileVO> queryWrapper = new QueryWrapper();
            queryWrapper.lambda().eq(FileVO::getConnectId,String.valueOf(i)).eq(FileVO::getFileType,FileEnum.CROP);
            FileVO fileVO = fileDao.selectOne(queryWrapper);
            if(fileVO!=null){
                FileUtil.deleteFile(fileVO.getFileAbsolutePath());
                fileDao.deleteById(fileVO.getFileId());
            }
        }
        return true;
    }

    public Result<Object> updateCrop(int cropId, String cropName, MultipartFile picture)  {
        CropVO cropVO = new CropVO();
        cropVO.setCropId(cropId);
        cropVO.setCropName(cropName);
        cropDao.updateById(cropVO);
        if(picture!=null){
            QueryWrapper<FileVO> queryWrapper = new QueryWrapper();
            queryWrapper.lambda().eq(FileVO::getConnectId,String.valueOf(cropId)).eq(FileVO::getFileType,FileEnum.CROP);
            FileVO fileVO = fileDao.selectOne(queryWrapper);
            FileUtil.deleteFile(fileVO.getFileAbsolutePath());
            try{
                String  absolutePath= FileUtil.uploadFile(FileEnum.CROP, "", picture);
                String  url = FileUtil.getFileUrl(absolutePath);
                fileVO.setFileAbsolutePath(absolutePath);
                fileVO.setUrl(url);
                fileDao.updateById(fileVO);
            }catch (IOException e){
                e.printStackTrace();
                return new DataBaseException().insertError(e);
            }

        }
        return ResultUtil.success();
    }

    public List<CropModel> listWithUrl() {
        List<CropModel> models = new ArrayList<>();
        QueryWrapper<CropVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByDesc(CropVO::getLastModifiedTime);
        List<CropVO> cropVOS = cropDao.selectList(queryWrapper);
        for (CropVO crop : cropVOS) {
            QueryWrapper<FileVO> fileVOQueryWrapper = new QueryWrapper<>();
            fileVOQueryWrapper.lambda().eq(FileVO::getConnectId,String.valueOf(crop.getCropId())).eq(FileVO::getFileType,FileEnum.CROP);
            FileVO fileVO = fileDao.selectOne(fileVOQueryWrapper);
            CropModel cropModel = new CropModel();
            cropModel.setCropId(crop.getCropId());
            cropModel.setCropName(crop.getCropName());
            if(fileVO!=null){
                cropModel.setUrl(fileVO.getUrl());
            }else {
                cropModel.setUrl("");
            }
            models.add(cropModel);
        }
        return models;
    }
}
