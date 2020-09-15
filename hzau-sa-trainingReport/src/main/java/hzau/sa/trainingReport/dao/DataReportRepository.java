package hzau.sa.trainingReport.dao;

import hzau.sa.trainingReport.entity.DataReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DataRepository的仓库接口
 * @author haokai
 */
public interface DataReportRepository extends MongoRepository<DataReport,String> {
    /**
     *
     * @param dataReportId 调查数据id
     * @return 调查数据
     */
    DataReport findByDataReportId(int dataReportId);

    /**
     *
     * @param dataReportId 调查数据id
     * @return 删除的id
     */
    long deleteByDataReportId(int dataReportId);

    /**
     *
     * @param ids 调查数据的id组
     * @return 删除的id
     */
    long deleteDataReportsByDataReportIdIn(List<Integer> ids);

    /**
     *
     * @param ids 作物id数组
     * @return  查询这些作物的调查数据
     */
    List<DataReport> findByCropIdIn(List<Integer> ids);

    /**
     *
     * @param cropId 作物id
     * @return 查询这个作物的调查数据
     */
    List<DataReport> findByCropId(int cropId);

    /**
     *
     * @param studentId 学生id
     * @param cropId 作物id
     * @return 该学生对该作物的调查数据列表
     */
    List<DataReport> findByStudentIdAndCropId(String studentId,int cropId);
}