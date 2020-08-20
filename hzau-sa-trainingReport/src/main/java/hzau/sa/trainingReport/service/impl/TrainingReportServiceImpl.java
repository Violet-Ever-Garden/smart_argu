package hzau.sa.trainingReport.service.impl;

import hzau.sa.trainingReport.entity.TrainingReportVO;
import hzau.sa.trainingReport.dao.TrainingReportDao;
import hzau.sa.trainingReport.entity.TrainingReportVO;
import hzau.sa.trainingReport.service.TrainingReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
        System.out.println(excelFilePath);
        return excelFilePath;
    }
}
