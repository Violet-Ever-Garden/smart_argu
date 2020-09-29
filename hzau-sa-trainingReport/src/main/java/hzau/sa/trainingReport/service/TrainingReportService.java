package hzau.sa.trainingReport.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.msg.entity.Result;
import hzau.sa.trainingReport.entity.TrainingReportPageWithoutFile;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.trainingReport.dao.TrainingReportDao;
import hzau.sa.trainingReport.entity.TrainingReportVO;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * trainingReport 服务实现类
 * @author lvhao
 * @date 2020-08-19
 */
public interface TrainingReportService {

    /**
     * 生成excel文件 返回excel文件所在的目录
     * @param cropId
     * @param classIds
     * @param teacherId
     * @return
     */
    public String excelDir(String cropId, String[] classIds, String teacherId);
    public Result deleteTrainingReport(Integer trainingReportId);
    public Result deleteTrainingReports(Integer[] trainingReportIds);
    public Result addTrainingReport(String studentId,Integer cropId,String trainingReportName, Integer batch, MultipartFile file);
    public Result updateTrainingReportByStudent(Integer trainingReportId,Integer cropId,String studentId,String trainingReportName,Integer batch,MultipartFile file);
    public Result updateTrainingReportByTeacher(Integer trainingReportId,String comments,Integer score,String teacherId);
    public IPage<TrainingReportPageWithoutFile> page(Page<TrainingReportPageWithoutFile> page,
                                                     Integer corpId,
                                                     String studentId,
                                                     String startTime,
                                                     String endTime,
                                                     String reviewStatus,
                                                     String trainingReportName);
    public Result studentView(Integer trainingReportId,String fileType);
    public Result teacherView(Integer trainingReportId,String fileType);
}