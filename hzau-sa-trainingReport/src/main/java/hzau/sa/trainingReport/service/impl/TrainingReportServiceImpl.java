package hzau.sa.trainingReport.service.impl;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.msg.dao.FileDao;
import hzau.sa.msg.entity.FileVO;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.FileEnum;
import hzau.sa.msg.excel.MyExcelUtil;
import hzau.sa.msg.util.FileUtil;
import hzau.sa.msg.util.ResultUtil;
import hzau.sa.trainingReport.entity.*;
import hzau.sa.trainingReport.dao.TrainingReportDao;
import hzau.sa.trainingReport.entity.TrainingReportVO;
import hzau.sa.trainingReport.service.TrainingReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.LinearRegressionFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 *  服务实现类
 *
 *
 * @author lvhao
 * @since 2020-08-19
 */
@Slf4j
@Service
public class TrainingReportServiceImpl extends ServiceImpl<TrainingReportDao, TrainingReportVO> implements TrainingReportService {

    @Value("${file.excel}")
    private String excelFilePath;

    @Resource
    private TrainingReportDao trainingReportDao;

    @Autowired
    private FileDao fileDao;

    @Override
    public String excelDir(String cropId, String[] classIds, String teacherId) {

        File file = new File(excelFilePath);
        if(!file.exists()){
            file.mkdirs();
        }

        try{
            for(String classId : classIds){
                List<ExportReport> exportReportList = trainingReportDao.queryExportReportByCropClassTeacherIds(Integer.valueOf(cropId),Integer.valueOf(classId),teacherId);
                String fileName = trainingReportDao.queryCropNameById(Integer.valueOf(cropId)) + "_" +
                        trainingReportDao.queryGradeNameByClassId(Integer.valueOf(classId)) + "_" +
                        trainingReportDao.queryClassNameById(Integer.valueOf(classId));
                MyExcelUtil.generateExcel(excelFilePath,fileName, ExportReport.class,exportReportList);
            }
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }
        return excelFilePath;
    }

    /**
     * 删除实训报告
     * @param trainingReportId
     * @return
     */
    @Override
    public Result deleteTrainingReport(Integer trainingReportId){
        //删除实训报告记录
        if (trainingReportDao.deleteById(trainingReportId)==0){
            return ResultUtil.databaseError("实训删除失败");
        }


        //删除文件
        QueryWrapper<FileVO> fileVOQueryWrapper = new QueryWrapper<>();
        fileVOQueryWrapper.lambda().eq(FileVO::getFileType,String.valueOf(FileEnum.TRAININGREPORT))
                .eq(FileVO::getConnectId,trainingReportId);
        FileVO fileVO = fileDao.selectOne(fileVOQueryWrapper);
        //删除文件本身
        FileUtil.deleteFile(fileVO.getFileAbsolutePath());
        //删除文件记录
        if (fileDao.delete(fileVOQueryWrapper)==0){
            return ResultUtil.error("文件删除失败");
        }
        return ResultUtil.success();
    }

    /**
     * 批量删除实训报告
     * @param trainingReportIds
     * @return
     */
    @Override
    public Result deleteTrainingReports(Integer[] trainingReportIds){
        //删除实训报告
        QueryWrapper<TrainingReportVO> trainingReportVOQueryWrapper = new QueryWrapper<>();
        trainingReportVOQueryWrapper.lambda().in(TrainingReportVO::getTrainingReportId, Arrays.asList(trainingReportIds));

        if (trainingReportDao.delete(trainingReportVOQueryWrapper)==0){
            return ResultUtil.error("批量删除失败");
        }

        //删除文件记录
        for (Integer id:trainingReportIds){
            QueryWrapper<FileVO> fileVOQueryWrapper = new QueryWrapper<>();
            fileVOQueryWrapper.lambda()
                    .eq(FileVO::getFileType,String.valueOf(FileEnum.TRAININGREPORT))
                    .eq(FileVO::getConnectId,id);
            FileVO fileVO = fileDao.selectOne(fileVOQueryWrapper);
            //删除文件本身
            FileUtil.deleteFile(fileVO.getFileAbsolutePath());
            //删除文件记录
            if (fileDao.delete(fileVOQueryWrapper)==0){
                return ResultUtil.error("批量删除文件失败");
            }
        }
        return ResultUtil.success();
    }

    /**
     * 增加实训报告表
     * @param studentId 学生id
     * @param cropId 作物id
     * @param trainingReportName 实训报告名
     * @param batch 批次
     * @param file 文件
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result addTrainingReport(String studentId, Integer cropId, String trainingReportName, Integer batch, MultipartFile file){
        //增加实训报告表

        //得到选择默认id
        String teacherId="admin";


        TrainingReportVO trainingReportVO = new TrainingReportVO(cropId,studentId,batch,trainingReportName,teacherId);

        //判断批次重复性
        QueryWrapper<TrainingReportVO> trainingReportVOQueryWrapper = new QueryWrapper<>();
        trainingReportVOQueryWrapper.lambda().eq(TrainingReportVO::getStudentId,studentId)
                .eq(TrainingReportVO::getBatch,batch).eq(TrainingReportVO::getCropId,cropId);


        TrainingReportVO trainingReportVOSelect = trainingReportDao.selectOne(trainingReportVOQueryWrapper);
        if (trainingReportVOSelect!=null){
            return ResultUtil.error("该批次已存在");
        }

        //增加
        int status=trainingReportDao.insert(trainingReportVO);
        if (status==0){
            return ResultUtil.error("实训报告增加失败");
        }




        String absolutePath=null;
        //增加文件表
        try {
            absolutePath = FileUtil.uploadFile(FileEnum.TRAININGREPORT, "trainingReport", file);
        } catch (IOException e) {
            e.printStackTrace();
            log.warn("文件增加失败1");
            return ResultUtil.error("文件增加失败");
        }
        String fileUrl = FileUtil.getFileUrl(absolutePath);
        FileVO fileVO = new FileVO();
        fileVO.setFileAbsolutePath(absolutePath);
        fileVO.setUrl(fileUrl);
        fileVO.setConnectId(String.valueOf(trainingReportVO.getTrainingReportId()));
        fileVO.setFileType(String.valueOf(FileEnum.TRAININGREPORT));

        if (fileDao.insert(fileVO)==0){
            log.warn("文件增加失败2");
            return ResultUtil.error("文件增加失败");
        }
        return ResultUtil.success();
    }



    /**
     * 学生修改实训报告
     * @param trainingReportId
     * @param trainingReportName
     * @param batch
     * @param file
     * @return
     */
    @Override
    public Result updateTrainingReportByStudent(Integer trainingReportId,Integer cropId,String studentId,String trainingReportName,Integer batch,MultipartFile file){
        //更新实训报告表
        TrainingReportVO trainingReportVO = new TrainingReportVO();
        trainingReportVO.setTrainingReportId(trainingReportId);
        trainingReportVO.setTrainingReportName(trainingReportName);
        trainingReportVO.setBatch(batch);

        QueryWrapper<TrainingReportVO> trainingReportVOQueryWrapper = new QueryWrapper<>();
        trainingReportVOQueryWrapper.lambda().eq(TrainingReportVO::getStudentId,studentId)
                .eq(TrainingReportVO::getBatch,batch).eq(TrainingReportVO::getCropId,cropId);

        TrainingReportVO trainingReportVOSelect = trainingReportDao.selectOne(trainingReportVOQueryWrapper);
        if (trainingReportVOSelect==null){
            if (trainingReportDao.updateById(trainingReportVO)==0){
                return ResultUtil.error("实训报告更新失败");
            }
        }else {
            if (trainingReportVOSelect.getTrainingReportId().equals(trainingReportId)){
                if (trainingReportDao.updateById(trainingReportVO)==0){
                    return ResultUtil.error("实训报告更新失败");
                }
            }else {
                return ResultUtil.error("该批次已存在");
            }
        }


        if (file!=null){
            //删除原来文件
            QueryWrapper<FileVO> fileVOQueryWrapper = new QueryWrapper<>();
            fileVOQueryWrapper.lambda().eq(FileVO::getFileType,FileEnum.TRAININGREPORT)
                    .eq(FileVO::getConnectId,String.valueOf(trainingReportId));
            FileVO fileVOSelect = fileDao.selectOne(fileVOQueryWrapper);
            FileUtil.deleteFile(fileVOSelect.getFileAbsolutePath());


            //更新文件表
            String absolutePath=null;
            try {
                absolutePath = FileUtil.uploadFile(FileEnum.TRAININGREPORT, "trainingReport", file);
            } catch (IOException e) {
                e.printStackTrace();
                return ResultUtil.error("文件修改失败");
            }
            String fileUrl = FileUtil.getFileUrl(absolutePath);
            FileVO fileVO = new FileVO();
            fileVO.setFileAbsolutePath(absolutePath);
            fileVO.setUrl(fileUrl);
            fileVO.setFileId(fileVOSelect.getFileId());

            if (fileDao.updateById(fileVO)==0){
                return ResultUtil.error("文件修改失败");
            }
        }
        return ResultUtil.success();
    }

    /**
     * 老师修改实训报告
     * @param trainingReportId
     * @param comments
     * @param score
     * @return
     */
    @Override
    public Result updateTrainingReportByTeacher(Integer trainingReportId,String comments, Integer score,String teacherId){
        TrainingReportVO trainingReportVO = new TrainingReportVO();
        trainingReportVO.setTrainingReportId(trainingReportId);
        trainingReportVO.setComments(comments);
        trainingReportVO.setScore(score);
        trainingReportVO.setReviewStatus("已评阅");
        trainingReportVO.setTeacherId(teacherId);

        if (trainingReportDao.updateById(trainingReportVO)==0){
            return ResultUtil.error("实训报告更新失败");
        }
        return ResultUtil.success();
    }

    /**
     * 分页查询
     * @param corpId
     * @param studentId
     * @param startTime
     * @param endTime
     * @param reviewStatus
     * @param trainingReportName
     * @return
     */
    @Override
    public IPage<TrainingReportPageWithoutFile> page(Page<TrainingReportPageWithoutFile> page,
                                                     Integer corpId,
                                                     String studentId,
                                                     String startTime,
                                                     String endTime,
                                                     String reviewStatus,
                                                     String trainingReportName){
        IPage<TrainingReportPageWithoutFile> iPage = trainingReportDao.page(page, corpId, studentId, startTime, endTime, reviewStatus,"%"+trainingReportName+"%");
        return iPage;
    }

    /**
     * 学生视角下的按实训报告id返回实训报告模型
     * @param trainingReportId
     * @param fileType
     * @return
     */
    @Override
    public Result studentView(Integer trainingReportId, String fileType){
        TrainingReportStudentView trainingReportStudentView = trainingReportDao.studentView(trainingReportId,fileType);
        String url=trainingReportStudentView.getUrl();
        String fileName=url.substring(url.lastIndexOf('/')+1);
        trainingReportStudentView.setFileName(fileName);
        return ResultUtil.success(trainingReportStudentView);
    }

    /**
     * 老师视角下的按实训报告id返回实训模型
     * @param trainingReportId
     * @param fileType
     * @return
     */
    @Override
    public Result teacherView(Integer trainingReportId, String fileType){
        TrainingReportTeacherView trainingReportTeacherView = trainingReportDao.teacherView(trainingReportId,fileType);
        return ResultUtil.success(trainingReportTeacherView);
    }
}
