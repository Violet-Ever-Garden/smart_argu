package hzau.sa.trainingReport.service.impl;

import hzau.sa.msg.excel.MyExcelUtil;
import hzau.sa.trainingReport.entity.ExportReport;
import hzau.sa.trainingReport.entity.TrainingReportVO;
import hzau.sa.trainingReport.dao.TrainingReportDao;
import hzau.sa.trainingReport.entity.TrainingReportVO;
import hzau.sa.trainingReport.service.TrainingReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuyihu
 * @since 2020-08-19
 */
@Service
public class TrainingReportServiceImpl extends ServiceImpl<TrainingReportDao, TrainingReportVO> implements TrainingReportService {

    @Value("${file.excel}")
    private String excelFilePath;

    @Resource
    private TrainingReportDao trainingReportDao;

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
}
