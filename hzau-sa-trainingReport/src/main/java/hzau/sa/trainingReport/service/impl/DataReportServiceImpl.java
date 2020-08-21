package hzau.sa.trainingReport.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.msg.exception.DataBaseException;
import hzau.sa.trainingReport.dao.DataReportDao;
import hzau.sa.trainingReport.dao.DataReportRepository;
import hzau.sa.trainingReport.entity.*;
import hzau.sa.trainingReport.service.DataReportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lvhao
 * @since 2020-08-14
 */
@Slf4j
@Service
public class DataReportServiceImpl extends ServiceImpl<DataReportDao, DataReportVO> implements DataReportService {

    @Autowired
    DataReportDao dataReportDao;
    @Autowired
    DataReportRepository dataReportRepository;

    String path = "C:\\Users\\Hasee\\Desktop\\新建文件夹\\wlw\\";
    //String path = "/home/hk/";

    @Transactional(rollbackFor = Exception.class)
    public boolean insert(DataReport dataReport) {
        DataReportVO dataReportVO = new DataReportVO();
        dataReportVO.setCropId(dataReport.getCropId());
        dataReportVO.setStudentId(dataReport.getStudentId());
        int insert=
                dataReportDao.insert(dataReportVO);
        if(0==insert){
            return false;
        }
        dataReport.setDataReportId(dataReportVO.getDataReportId());
        log.info(dataReport.toString());
        DataReport save =
                dataReportRepository.save(dataReport);
        if(null == save){
            return false;
        }
        return true;
    }

    public IPage<DataReportModel> selectDataReportModelPage(Page<DataReportModel> page, int cropId, String studentId) {
        return dataReportDao.selectDataReportModelPage(page,cropId,studentId);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateDataReport(DataReport dataReport) {
        DataReport save = dataReportRepository.save(dataReport);
        if(save.getId()!=dataReport.getId()){
            return false;
        }else {
            return true;
        }
    }

    @Transactional(rollbackFor = Exception.class)
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

    /**
     * 通过班级id和作物id
     */
    public IPage<StudentReportModel> selectStudentByClassAndCrop(Page<StudentReportModel> page, int classId,  int cropId, String studentName){
        IPage<StudentReportModel> studentReportModels = dataReportDao.selectStudentByClassAndCrop(page,classId,cropId,"%"+studentName+"%");
        return studentReportModels;
    }


    /**
     *导出调查报告文件
     */
    public String excelDir(ArrayList<Integer> ids,int cropId,String teacherId) throws IOException {

        //获取作物的额外参数
        List<String> extraParameters = dataReportDao.selectParametersByCropId(cropId);
        List<ClassDataReport> classDataReports = dataReportDao.selectStudentByClass(cropId, ids,teacherId);
        List<DataReport> dataReports = dataReportRepository.findByCropId(cropId);
        //将mongodb中的数据和mysql中数据联系起来
        for(ClassDataReport classDataReport : classDataReports){
            cycleForStudent(cropId, dataReports, classDataReport);
        }
        for (ClassDataReport classDataReport : classDataReports){
            String className = classDataReport.getClassName();
            for(StudentDataReport studentDataReport : classDataReport.getStudentDataReports()){
                //创建工作簿
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("调查报告");
                //设置title
                setFirstRow(sheet,extraParameters);
                //填充后置数据
                setFollowRow(sheet,extraParameters,studentDataReport.getCropDataList());
                //写入地址
                File file = new File(path);
                if(file.exists()==false){
                    file.mkdirs();
                }
                FileOutputStream fileOut = new FileOutputStream(new File(file,className+"_"+studentDataReport.getStudentName()+"_"+ System.currentTimeMillis() + ".xlsx"));
                workbook.write(fileOut);
                workbook.close();
            }
        }
        return path;
    }

    public void setFirstRow(Sheet sheet,List<String> extraParameters){
        Row title = sheet.createRow(0);
        title.createCell(0).setCellValue("属性");
        title.createCell(1).setCellValue("检测时间");
        title.createCell(2).setCellValue("生育期");
        title.createCell(3).setCellValue("处理");
        for(int i=0;i<extraParameters.size();i++){
            title.createCell(i+4).setCellValue(extraParameters.get(i));
        }
        title.createCell(4+extraParameters.size()).setCellValue("数据");
        title.createCell(5+extraParameters.size()).setCellValue("平均值");
    }

    public void setFollowRow(Sheet sheet,List<String> extraParameters,List<CropData> cropDataList){
        int n = cropDataList.size();
        for(int i = 0 ; i < n ; i++){
            CropData cropData = cropDataList.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(cropData.getCropProperty());
            row.createCell(1).setCellValue(cropData.getDetectionTime().toString());
            row.createCell(2).setCellValue(cropData.getGrowthPeriod());
            row.createCell(3).setCellValue(cropData.getProcess());
            for(int j=0;j<extraParameters.size();j++){
                row.createCell(j+4).setCellValue(cropData.getExtraParam().get(extraParameters.get(j)));
            }
            row.createCell(4+extraParameters.size()).setCellValue(cropData.getData().toString());
            row.createCell(5+extraParameters.size()).setCellValue(cropData.getAverage());
        }
    }

    public List<CropDataReport> getStatisticalAnalysis(ArrayList<Integer> ids) {
        List<DataReport> dataReports = dataReportRepository.findByCropIdIn(ids);
        List<CropDataReport> cropDataReports = dataReportDao.selectCropDataReportList(ids);
        //对作物进行遍历
        for (CropDataReport cropDataReport : cropDataReports) {
            int cropId = cropDataReport.getCropId();
            //对年级进行遍历
            for ( GradeDataReport gradeDataReport : cropDataReport.getGradeDataReports()){
                //对班级进行遍历
                for( ClassDataReport classDataReport : gradeDataReport.getClassDataReports()){
                    //对学生进行遍历
                    cycleForStudent(cropId, dataReports, classDataReport);
                }
            }
        }
        return cropDataReports;
    }

    private void cycleForStudent(int cropId, List<DataReport> dataReports, ClassDataReport classDataReport) {
        for (StudentDataReport studentDataReport:classDataReport.getStudentDataReports()){
            String studentId = studentDataReport.getStudentId();
            studentDataReport.setCropDataList(new ArrayList<>());
            for(int i=0;i<dataReports.size();i++){
                DataReport dataReport = dataReports.get(i);
                if(dataReport.getStudentId().equals(studentId) && dataReport.getCropId()==cropId){
                    studentDataReport.getCropDataList().addAll(dataReport.getCropDatas());
                    dataReports.remove(i);
                    i--;
                }
            }
        }
    }
}
