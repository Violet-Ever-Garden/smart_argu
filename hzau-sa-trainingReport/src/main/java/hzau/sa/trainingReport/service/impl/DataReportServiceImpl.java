package hzau.sa.trainingReport.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.msg.exception.DataBaseException;
import hzau.sa.trainingReport.dao.DataReportDao;
import hzau.sa.trainingReport.dao.DataReportRepository;
import hzau.sa.trainingReport.entity.DataReport;
import hzau.sa.trainingReport.entity.DataReportModel;
import hzau.sa.trainingReport.entity.DataReportVO;
import hzau.sa.trainingReport.service.DataReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lvhao
 * @since 2020-08-14
 */
@Service
public class DataReportServiceImpl extends ServiceImpl<DataReportDao, DataReportVO> implements DataReportService {

    @Autowired
    DataReportDao dataReportDao;
    @Autowired
    DataReportRepository dataReportRepository;

    public boolean insert(DataReport dataReport) {
        DataReportVO dataReportVO = new DataReportVO();
        dataReportVO.setCropId(dataReport.getCropId());
        dataReportVO.setStudentId(dataReport.getStudentId());
        int insert= dataReportDao.insert(dataReportVO);
        if(0==insert){
            return false;
        }
        dataReport.setDataReportId(dataReportVO.getDataReportId());
        DataReport save = dataReportRepository.save(dataReport);
        if(null == save){
            return false;
        }
        return true;
    }

    public List<DataReportModel> selectDataReportModelPage(Page<DataReportModel> page, int cropId, String studentId) {
        return dataReportDao.selectDataReportModelPage(page,cropId,studentId);
    }

    public boolean updateDataReport(DataReport dataReport) {
        DataReport save = dataReportRepository.save(dataReport);
        if(save.getId()!=dataReport.getId()){
            return false;
        }else {
            return true;
        }
    }

    public boolean deleteByDataReportId(int dataReportId) {
        int i = dataReportDao.deleteById(dataReportId);
        if(0==i){
            return false;
        }
        DataReport dataReport = dataReportRepository.deleteByDataReportId(dataReportId);
        if(null==dataReport){
            return false;
        }
        return true;
    }
}
