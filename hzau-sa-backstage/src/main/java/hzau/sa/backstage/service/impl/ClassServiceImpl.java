package hzau.sa.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.dao.*;
import hzau.sa.backstage.entity.*;
import hzau.sa.backstage.service.ClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * class 服务实现类
 *
 *
 * @author lvhao
 * @since 2020-08-12
 */
@Service
@Slf4j
public class ClassServiceImpl extends ServiceImpl<ClassDao, ClassVO> implements ClassService {

    @Resource
    private GradeDao gradeDao;

    @Resource
    private AsClassfieldDao asClassfieldDao;

    @Resource
    private AsClassvideomonitorDao asClassvideomonitorDao;

    @Resource
    private AsClasscropDao asClasscropDao;

    @Resource
    private AsClasscontrolinteractionDao asClasscontrolinteractionDao;

    @Resource
    private AsClasssensorDao asClasssensorDao;

    @Resource
    private ClassDao classDao;

    @Override
    public Map findClass(Page page, QueryWrapper queryWrapper) {

        Map resultMap = new HashMap<>();

        IPage pageInfo = classDao.selectPage(page,queryWrapper);

        List<ClassManage> classManageList = new ArrayList<>();

        List<ClassVO> classVOList = pageInfo.getRecords();

        for(ClassVO classVO : classVOList){
            ClassManage classManage = new ClassManage();
            classManage.setClassId(classVO.getClassId());
            classManage.setClassName(classVO.getClassName());
            classManage.setClassGrade(gradeDao.selectGradeNameById(classVO.getGradeId()));
            classManage.setClassFields(asClassfieldDao.queryFieldNamesByClassId(classVO.getClassId()));
            classManage.setClassMonitor(asClassvideomonitorDao.queryMonitorNamesByClassId(classVO.getClassId()));
            classManage.setClassWaterFertilizerMachine(asClasscontrolinteractionDao.queryNamesByClassId(classVO.getClassId()));
            classManage.setClassSensor(asClasssensorDao.queryNameById(classVO.getClassId()));
            classManage.setClassCrop(asClasscropDao.queryNameById(classVO.getClassId()));
            classManageList.add(classManage);
        }

        resultMap.put("total",pageInfo.getTotal());
        resultMap.put("rows",classManageList);

        return resultMap;
    }

    @Override
    public List<Integer> classFields(Integer classId) {
        return asClassfieldDao.queryFieldIdsByClassId(classId);
    }

    @Override
    public List<Integer> classMonitors(Integer classId) {
        return asClassvideomonitorDao.queryMonitorIdsByClassId(classId);
    }



    @Override
    public boolean updateClassMessage(ClassManage classManage) {


        try{
            ClassVO classVO = classDao.selectById(classManage.getClassId());
            classVO.setClassName(classManage.getClassName());
            classVO.setGradeId(gradeDao.selectGradeIdByName(classManage.getClassGrade()));
            classDao.updateById(classVO);
            int classId = classVO.getClassId();
            //地块差异
            List<Integer> fieldsBefore = asClassfieldDao.queryFieldIdsByClassId(classVO.getClassId());
            List<Integer> fieldsNow = asClassfieldDao.queryFieldIdsByNames(classManage.getClassFields());
            List<Integer> fieldAdd = getDifferenceSet(fieldsNow,fieldsBefore);
            List<Integer> fieldDelete = getDifferenceSet(fieldsBefore,fieldsNow);
            if(fieldAdd.size()>0){
                for(int fieldId:fieldAdd ){
                    AsClassfieldVO asClassfieldVO = new AsClassfieldVO();
                    asClassfieldVO.setClassId(classId);
                    asClassfieldVO.setFieldId(fieldId);
                    asClassfieldDao.insert(asClassfieldVO);
                }
            }
            if(fieldDelete.size()>0){
                for(int fieldId:fieldDelete){
                    QueryWrapper<AsClassfieldVO> queryWrapper = new QueryWrapper<>();
                    queryWrapper.lambda().eq(AsClassfieldVO::getClassId,classId)
                            .eq(AsClassfieldVO::getFieldId,fieldId);
                    asClassfieldDao.delete(queryWrapper);
                }
            }
            //视频差异
            List<Integer> monitorBefore = asClassvideomonitorDao.queryMonitorIdsByClassId(classId);
            List<Integer> monitorNow = asClassvideomonitorDao.queryVidioMonitorIdsByNames(classManage.getClassMonitor());
            List<Integer> monitorAdd = getDifferenceSet(monitorNow,monitorBefore);
            List<Integer> monitorDelete = getDifferenceSet(monitorBefore,monitorNow);
            if(monitorAdd.size()>0){
                for(int monitorId:monitorAdd ){
                    AsClassvideomonitorVO asClassvideomonitorVO = new AsClassvideomonitorVO();
                    asClassvideomonitorVO.setClassId(classId);
                    asClassvideomonitorVO.setVideoMonitorId(monitorId);
                    asClassvideomonitorDao.insert(asClassvideomonitorVO);
                }
            }
            if(monitorDelete.size()>0){
                for(int monitorId:monitorDelete){
                    QueryWrapper<AsClassvideomonitorVO> queryWrapper = new QueryWrapper<>();
                    queryWrapper.lambda().eq(AsClassvideomonitorVO::getClassId,classId)
                            .eq(AsClassvideomonitorVO::getVideoMonitorId,monitorId);
                    asClassvideomonitorDao.delete(queryWrapper);
                }
            }
            //作物差异
            List<Integer> cropsBefore = asClasscropDao.queryCropIdsByClassId(classVO.getClassId());
            List<Integer> cropsNow = asClasscropDao.queryFieldIdsByNames(classManage.getClassCrop());
            List<Integer> cropAdd = getDifferenceSet(cropsNow,cropsBefore);
            List<Integer> cropDelete = getDifferenceSet(cropsBefore,cropsNow);
            if(cropAdd.size()>0){
                for(int cropId:cropAdd ){
                    AsClasscropVO asClasscropVO = new AsClasscropVO();
                    asClasscropVO.setClassId(classId);
                    asClasscropVO.setCropId(cropId);
                    asClasscropDao.insert(asClasscropVO);
                }
            }
            if(cropDelete.size()>0){
                for(int cropId:cropDelete){
                    QueryWrapper<AsClasscropVO> queryWrapper = new QueryWrapper<>();
                    queryWrapper.lambda().eq(AsClasscropVO::getClassId,classId)
                            .eq(AsClasscropVO::getCropId,cropId);
                    asClasscropDao.delete(queryWrapper);
                }
            }
            //水肥机差异
            List<Integer> controlinteractionsBefore = asClasscontrolinteractionDao.queryControlinteractionIdsByClassId(classVO.getClassId());
            List<Integer> controlinteractionsNow = asClasscontrolinteractionDao.queryControlinteractionIdsByNames(classManage.getClassWaterFertilizerMachine());
            List<Integer> controlinteractionAdd = getDifferenceSet(controlinteractionsNow,controlinteractionsBefore);
            List<Integer> controlinteractionDelete = getDifferenceSet(controlinteractionsBefore,controlinteractionsNow);
            if(controlinteractionAdd.size()>0){
                for(int id:controlinteractionAdd ){
                    AsClasscontrolinteractionVO asClasscontrolinteractionVO = new AsClasscontrolinteractionVO();
                    asClasscontrolinteractionVO.setClassId(classId);
                    asClasscontrolinteractionVO.setControlInteractionId(id);
                    asClasscontrolinteractionDao.insert(asClasscontrolinteractionVO);
                }
            }
            if(controlinteractionDelete.size()>0){
                for(int id:controlinteractionDelete){
                    QueryWrapper<AsClasscontrolinteractionVO> queryWrapper = new QueryWrapper<>();
                    queryWrapper.lambda().eq(AsClasscontrolinteractionVO::getClassId,classId)
                            .eq(AsClasscontrolinteractionVO::getControlInteractionId,id);
                    asClasscontrolinteractionDao.delete(queryWrapper);
                }
            }
            //传感器差异
            List<Integer> sensorsBefore = asClasssensorDao.querySensorIdsByClassId(classVO.getClassId());
            List<Integer> sensorsNow = asClasssensorDao.querySensorIdsByNames(classManage.getClassSensor());
            List<Integer> sensorsAdd = getDifferenceSet(sensorsNow,sensorsBefore);
            List<Integer> sensorsDelete = getDifferenceSet(sensorsBefore,sensorsNow);
            if(sensorsAdd.size()>0){
                for(int id:sensorsAdd ){
                    AsClasssensorVO asClasssensorVO = new AsClasssensorVO();
                    asClasssensorVO.setClassId(classId);
                    asClasssensorVO.setSensorId(id);
                    asClasssensorDao.insert(asClasssensorVO);
                }
            }
            if(sensorsDelete.size()>0){
                for(int id:sensorsDelete){
                    QueryWrapper<AsClasssensorVO> queryWrapper = new QueryWrapper<>();
                    queryWrapper.lambda().eq(AsClasssensorVO::getClassId,classId)
                            .eq(AsClasssensorVO::getSensorId,id);
                    asClasssensorDao.delete(queryWrapper);
                }
            }
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }

        return true;
    }

    @Override
    public ClassManage insertClassMessage(ClassManage classManage) {
        try{
            ClassVO classVO = insertClassVO(classManage);

            Integer classId = classDao.selectOne(new QueryWrapper<ClassVO>()
                    .lambda()
                    .eq(ClassVO::getClassName,classVO.getClassName())
                    .eq(ClassVO::getGradeId,classVO.getGradeId())).getClassId();
            classManage.setClassId(classId);

            List<String> classFields = classManage.getClassFields();
            for(String fields : classFields){
                insertAsField(classId,fields);
            }

            List<String> classMonitors = classManage.getClassMonitor();
            for(String monitors : classMonitors){
                insertAsMonitor(classId,monitors);
            }

            List<String> classCrop = classManage.getClassCrop();
            for(String cropName : classCrop){
                insertAsCrop(classId,cropName);
            }

            List<String> classWaterFertilizerMachine = classManage.getClassWaterFertilizerMachine();
            for (String waterFertilizerMachine:classWaterFertilizerMachine){
                insertASControlInteraction(classId,waterFertilizerMachine);
            }

            List<String> classSensor = classManage.getClassSensor();
            for(String sensorName : classSensor){
                insertAsSensor(classId,sensorName);
            }
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }
        return classManage;
    }

    public ClassVO insertClassVO(ClassManage classManage){
        ClassVO classVO = new ClassVO();
        classVO.setClassName(classManage.getClassName());
        classVO.setGradeId(gradeDao.selectGradeIdByName(classManage.getClassGrade()));
        classDao.insert(classVO);
        return classVO;
    }

    public void insertAsSensor(int classId,String sensorName){
        AsClasssensorVO asClasssensorVO = new AsClasssensorVO();
        asClasssensorVO.setClassId(classId);
        asClasssensorVO.setSensorId(asClasssensorDao.querySensorIdByName(sensorName));
        asClasssensorDao.insert(asClasssensorVO);
    }
    public void insertASControlInteraction(int classId , String waterFertilizerMachine){
        AsClasscontrolinteractionVO asClasscontrolinteractionVO = new AsClasscontrolinteractionVO();
        asClasscontrolinteractionVO.setClassId(classId);
        asClasscontrolinteractionVO.setControlInteractionId(asClasscontrolinteractionDao.queryControlInteractionIdByName(waterFertilizerMachine));
        asClasscontrolinteractionDao.insert(asClasscontrolinteractionVO);
    }
    public void insertAsCrop(int classId,String cropName){
        AsClasscropVO asClasscropVO = new AsClasscropVO();
        asClasscropVO.setClassId(classId);
        asClasscropVO.setCropId(asClasscropDao.queryCropIdByName(cropName));
        asClasscropDao.insert(asClasscropVO);
    }
    public void insertAsMonitor(int classId,String monitor){
        AsClassvideomonitorVO asClassvideomonitorVO = new AsClassvideomonitorVO();
        asClassvideomonitorVO.setClassId(classId);
        asClassvideomonitorVO.setVideoMonitorId(asClassvideomonitorDao.queryMonitorIdByName(monitor));
        asClassvideomonitorDao.insert(asClassvideomonitorVO);
    }
    public void insertAsField(int classId,String fields){
        AsClassfieldVO asClassfieldVO = new AsClassfieldVO();
        asClassfieldVO.setClassId(classId);
        asClassfieldVO.setFieldId(asClassfieldDao.queryFieldIdByName(fields));
        asClassfieldDao.insert(asClassfieldVO);
    }

    public List<Integer> getDifferenceSet(List<Integer> before,List<Integer> now){
        ArrayList<Integer> integers = new ArrayList<>(before);
        integers.removeAll(now);
        return integers;
    }

    @Override
    public List<String> queryAllClass() {

        List<String> allClass = new ArrayList<>();

        List<ClassVO> listClass = classDao.selectList(null);
        for(ClassVO classVO : listClass){
            allClass.add(classVO.getClassName());
        }

        return allClass;
    }
}
