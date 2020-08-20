package hzau.sa.trainingReport.service;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.trainingReport.dao.TrainingReportDao;
import hzau.sa.trainingReport.entity.TrainingReportVO;
import org.springframework.web.bind.annotation.RequestParam;

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

}