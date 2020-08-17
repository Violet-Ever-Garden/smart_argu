package hzau.sa.trainingReport.dao;

import hzau.sa.trainingReport.entity.DataReport;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * DataRepository的仓库接口
 * @author haokai
 */
public interface DataReportRepository extends MongoRepository<DataReport,String> {
    DataReport findByDataReportId(int dataReportId);
    DataReport deleteByDataReportId(int dataReportId);
}