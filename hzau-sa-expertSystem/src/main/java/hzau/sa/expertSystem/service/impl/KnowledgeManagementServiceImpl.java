package hzau.sa.expertSystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.expertSystem.dao.KnowledgeManagementDao;
import hzau.sa.expertSystem.entity.KnowledgeManagementModel;
import hzau.sa.expertSystem.entity.KnowledgeManagementVO;
import hzau.sa.expertSystem.entity.KnowledgeManagementView;
import hzau.sa.expertSystem.service.KnowledgeManagementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.msg.dao.FileDao;
import hzau.sa.msg.entity.FileVO;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.FileEnum;
import hzau.sa.msg.util.FileUtil;
import hzau.sa.msg.util.ResultUtil;
import io.swagger.models.auth.In;
import org.apache.poi.ss.formula.functions.LinearRegressionFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuyihu
 * @since 2020-08-30
 */
@Service
public class KnowledgeManagementServiceImpl extends ServiceImpl<KnowledgeManagementDao, KnowledgeManagementVO> implements KnowledgeManagementService {
    @Autowired
    private KnowledgeManagementDao knowledgeManagementDao;

    @Autowired
    private FileDao fileDao;

    /**
     * 增加知识库
     * @param knowledgeManageName
     * @param knowledgeCategoryName
     * @param knowledgeIntroduction
     * @param knowledgeContentHtml
     * @param file
     * @return
     */
    @Override
    public Result addKnowledge(String knowledgeManageName, String knowledgeCategoryName, String knowledgeIntroduction, String knowledgeContentHtml,MultipartFile file){
        //判断是否重名
        if (isKnowledgeExist(knowledgeManageName)){
            return ResultUtil.error("知识库名字已存在");
        }

        //构造知识库实体
        KnowledgeManagementVO knowledgeManagementVO = new KnowledgeManagementVO();
        knowledgeManagementVO.setKnowledgeManageName(knowledgeManageName);
        Integer categoryId=knowledgeManagementDao.queryCategoryIdByName(knowledgeCategoryName);
        knowledgeManagementVO.setKnowledgeCategoryId(categoryId);
        knowledgeManagementVO.setKnowledgeIntroduction(knowledgeIntroduction);
        knowledgeManagementVO.setKnowledgeContent(knowledgeContentHtml);

        //对知识库插入
        if (knowledgeManagementDao.insert(knowledgeManagementVO)==0){
            return ResultUtil.error("知识库增加失败");
        }

        //构造文件实体
        String absolutePath=null;
        try {
            absolutePath= FileUtil.uploadFile(FileEnum.KNOWLEDGE,"knowledge",file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url= FileUtil.getFileUrl(absolutePath);
        String fileType=String.valueOf(FileEnum.KNOWLEDGE);
        Integer connectId=knowledgeManagementVO.getKnowledgeManageId();

        FileVO fileVO = new FileVO();

        fileVO.setFileAbsolutePath(absolutePath);
        fileVO.setUrl(url);
        fileVO.setFileType(fileType);
        fileVO.setConnectId(String.valueOf(connectId));

        //插入
        if (fileDao.insert(fileVO)==0){
            return ResultUtil.error("文件插入失败");
        }

        return ResultUtil.success();
    }

//    /**
//     * 根据富文本内容转化得到新的富文本内容
//     * @param html
//     * @return
//     */
//    public String getKnowledgeContentByHtml(String html){
//        //通过正则得到所有base64的数据标签的位置
//        String patten="<img src=.*?>";
//        Pattern compile = Pattern.compile(patten);
//        Matcher matcher = compile.matcher(html);
//
//        ArrayList<String> imgs= new ArrayList<>();
//        //将base64转化为文件存储并得到url
//        //将原html的img标签里面的src内容替换成url
//
//        return "";
//    }

    /**
     * 批量删除知识库
     * @param knowledgeManagementIds
     * @return
     */
    @Override
    public Result deleteKnowledges(String[] knowledgeManagementIds){
        ArrayList<Integer> integers = new ArrayList<>();
        for (String s:knowledgeManagementIds){
            integers.add(Integer.valueOf(s));
        }

        //判断存在性
        if (integers.isEmpty()){
            return ResultUtil.error("至少选择一条数据");
        }

        //删除知识库
        int i = knowledgeManagementDao.deleteBatchIds(integers);
        if (i==0){
            return ResultUtil.error("批量删除失败");
        }

        //删除文件
        QueryWrapper<FileVO> fileVOQueryWrapper = new QueryWrapper<>();
        fileVOQueryWrapper.lambda().eq(FileVO::getFileType,String.valueOf(FileEnum.KNOWLEDGE))
                .in(FileVO::getConnectId,knowledgeManagementIds);

        if (fileDao.delete(fileVOQueryWrapper)==0){
            return ResultUtil.error("文件批量删除失败");
        }

        return ResultUtil.success();
    }

    /**
     * 更新知识库
     * @param knowledgeManageId
     * @param knowledgeManageName
     * @param knowledgeCategoryName
     * @param knowledgeIntroduction
     * @param knowledgeContentHtml
     * @param file
     * @return
     */
    @Override
    public Result updateKnowledge(Integer knowledgeManageId,String knowledgeManageName, String knowledgeCategoryName, String knowledgeIntroduction, String knowledgeContentHtml,MultipartFile file){
        QueryWrapper<KnowledgeManagementVO> knowledgeManagementVOQueryWrapper = new QueryWrapper<>();
        knowledgeManagementVOQueryWrapper.lambda().eq(KnowledgeManagementVO::getKnowledgeManageId,knowledgeManageId);
        KnowledgeManagementVO knowledgeManagementVOSelect = knowledgeManagementDao.selectOne(knowledgeManagementVOQueryWrapper);

        //判断是否重名
        if (isKnowledgeExist(knowledgeManageName) && (!knowledgeManageName.equals(knowledgeManagementVOSelect.getKnowledgeManageName()))){
            return ResultUtil.error("知识库名字已存在");
        }

        knowledgeManagementVOSelect.setKnowledgeManageName(knowledgeManageName);
        Integer categoryId=knowledgeManagementDao.queryCategoryIdByName(knowledgeCategoryName);
        knowledgeManagementVOSelect.setKnowledgeCategoryId(categoryId);
        knowledgeManagementVOSelect.setKnowledgeIntroduction(knowledgeIntroduction);
        knowledgeManagementVOSelect.setKnowledgeContent(knowledgeContentHtml);

        //对知识库插入
        if (knowledgeManagementDao.updateById(knowledgeManagementVOSelect)==0){
            return ResultUtil.error("知识库更新失败");
        }




        //删除原来文件
        QueryWrapper<FileVO> fileVOQueryWrapper = new QueryWrapper<>();
        fileVOQueryWrapper.lambda().eq(FileVO::getFileType,String.valueOf(FileEnum.KNOWLEDGE))
                .eq(FileVO::getConnectId,knowledgeManageId);
        FileVO fileVOSelect = fileDao.selectOne(fileVOQueryWrapper);
        FileUtil.deleteFile(fileVOSelect.getFileAbsolutePath());

        //构造文件实体
        String absolutePath=null;
        try {
            absolutePath= FileUtil.uploadFile(FileEnum.KNOWLEDGE,"knowledge",file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url= FileUtil.getFileUrl(absolutePath);
        String fileType=String.valueOf(FileEnum.KNOWLEDGE);


        fileVOSelect.setFileAbsolutePath(absolutePath);
        fileVOSelect.setUrl(url);


        //插入
        if (fileDao.updateById(fileVOSelect)==0){
            return ResultUtil.error("文件更新失败");
        }

        return ResultUtil.success();
    }

    /**
     * 分页
     * @param page
     * @param name
     * @param category
     * @return
     */
    @Override
    public IPage<KnowledgeManagementModel> page(Page<KnowledgeManagementModel> page, String name, String category){
        IPage<KnowledgeManagementModel> iPage = knowledgeManagementDao.page(page, "%" + name + "%", category);
        return iPage;
    }

    /**
     * 按id进入知识库界面
     * @param knowledgeManageId
     * @return
     */
    @Override
    public KnowledgeManagementView queryKnowledgeById(Integer knowledgeManageId){
        KnowledgeManagementView knowledgeManagementView = knowledgeManagementDao.queryKnowledgeById(knowledgeManageId, String.valueOf(FileEnum.KNOWLEDGE));
        return knowledgeManagementView;

    }

    /**
     * 判断是否重名存在
     * @param knowledgeManageName
     * @return
     */
    public boolean isKnowledgeExist(String knowledgeManageName){
        QueryWrapper<KnowledgeManagementVO> knowledgeManagementVOQueryWrapper = new QueryWrapper<>();
        knowledgeManagementVOQueryWrapper.lambda().eq(KnowledgeManagementVO::getKnowledgeManageName,knowledgeManageName);
        KnowledgeManagementVO knowledgeManagementVO = knowledgeManagementDao.selectOne(knowledgeManagementVOQueryWrapper);
        if (knowledgeManagementVO==null){
            return false;
        }
        return true;
    }
}
