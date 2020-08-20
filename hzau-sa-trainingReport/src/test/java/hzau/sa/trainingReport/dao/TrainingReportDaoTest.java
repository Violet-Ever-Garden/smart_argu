package hzau.sa.trainingReport.dao;

import hzau.sa.msg.excel.MyExcelUtil;
import hzau.sa.trainingReport.TrainingReportApplication;
import hzau.sa.trainingReport.entity.ExportReport;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author LvHao
 * @Description :
 * @date 2020-08-20 12:27
 */
@Slf4j
@SpringBootTest(classes = TrainingReportApplication.class)
class TrainingReportDaoTest {

    @Resource
    private TrainingReportDao trainingReportDao;

    @Test
    void queryExportReportByCropClassTeacherIds() {
        Integer classId = 1;
        Integer cropId = 17;
        String teacherId = "1122334455";

        List<ExportReport> exportReportList = trainingReportDao.queryExportReportByCropClassTeacherIds(cropId,classId,teacherId);
        for(ExportReport exportReport : exportReportList){
            log.info("exportReport:" + exportReport);
        }

        try{
            MyExcelUtil.generateExcel("C:\\Users\\LvHao\\Desktop\\物联网项目",cropId + "-" + teacherId,ExportReport.class,exportReportList);
        }catch (Exception e){
            log.error(e.toString());
        }
    }
}