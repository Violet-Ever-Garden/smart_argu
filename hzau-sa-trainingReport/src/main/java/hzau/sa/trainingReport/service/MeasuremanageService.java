package hzau.sa.trainingReport.service;

import com.baomidou.mybatisplus.extension.service.IService;
import hzau.sa.trainingReport.entity.MeasureManageRequest;
import hzau.sa.trainingReport.entity.MeasureManageResponse;
import hzau.sa.trainingReport.entity.MeasuremanageVO;

import java.io.IOException;
import java.util.List;

/**
 * measuremanage 服务实现类
 * @author lvhao
 * @date 2020-08-18
 */
public interface MeasuremanageService  extends IService<MeasuremanageVO> {

    /**
     * 增加一条措施
     * @param measureManageRequest
     * @return
     * @throws IOException
     */
    public boolean insertMeasure(MeasureManageRequest measureManageRequest) throws IOException;

    /**
     * 查询学生关于一种作物的所有措施
     * @param studentId
     * @param cropName
     * @return
     */
    public List<MeasureManageResponse> queryMeasure(String studentId,String cropName);

    /**
     * 更新一条措施信息
     * @param measureManageRequest
     * @param measureManageId
     * @param ids
     * @return
     * @throws IOException
     */
    public boolean updateMeasure(MeasureManageRequest measureManageRequest,Integer measureManageId,String[] ids) throws IOException;
}