package hzau.sa.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.dao.AsClassfieldDao;
import hzau.sa.backstage.dao.AsClassmonitorDao;
import hzau.sa.backstage.dao.GradeDao;
import hzau.sa.backstage.entity.AsClassfieldVO;
import hzau.sa.backstage.entity.AsClassmonitorVO;
import hzau.sa.backstage.entity.ClassManage;
import hzau.sa.backstage.entity.ClassVO;
import hzau.sa.backstage.dao.ClassDao;
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
    private AsClassmonitorDao asClassmonitorDao;

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
            classManage.setClassFields(asClassfieldDao.queryFieldIdsByClassId(classVO.getClassId()));
            classManage.setClassMonitor(asClassmonitorDao.queryMonitorIdsByClassId(classVO.getClassId()));
            classManageList.add(classManage);
        }

        resultMap.put("total",pageInfo.getTotal());
        resultMap.put("rows",classManageList);

        return resultMap;
    }

    @Override
    public List<String> classFields(Integer classId) {
        return asClassfieldDao.queryFieldIdsByClassId(classId);
    }

    @Override
    public List<String> classMonitors(Integer classId) {
        return asClassmonitorDao.queryMonitorIdsByClassId(classId);
    }

    @Override
    public ClassManage updateClassMessage(ClassManage classManage) {

        ClassManage classManageResult = new ClassManage();

        try{
            ClassVO classVO = classDao.selectById(classManage.getClassId());
            classVO.setClassName(classManage.getClassName());
            classVO.setGradeId(gradeDao.selectGradeIdByName(classManage.getClassGrade()));
            classDao.updateById(classVO);

            List<String> fieldsBefore = asClassfieldDao.queryFieldIdsByClassId(classVO.getClassId());
            List<String> fieldsNow = classManage.getClassFields();
            List<String> subtraction = null;

            if(fieldsNow.size() > fieldsBefore.size()){
                subtraction = fieldsNow.stream().filter(item -> !fieldsBefore.contains(item)).collect(Collectors.toList());
                for(String fieldName : subtraction){
                    AsClassfieldVO asClassfieldVO = new AsClassfieldVO();
                    asClassfieldVO.setClassId(classVO.getClassId());
                    asClassfieldVO.setFieldId(asClassfieldDao.queryFieldIdByName(fieldName));
                    asClassfieldDao.insert(asClassfieldVO);
                }
            }
            if(fieldsNow.size() < fieldsBefore.size()){
                subtraction = fieldsBefore.stream().filter(item -> !fieldsNow.contains(item)).collect(Collectors.toList());
                for(String fieldName : subtraction){
                    Integer fieldId = asClassfieldDao.queryFieldIdByName(fieldName);
                    asClassfieldDao.delete(new QueryWrapper<AsClassfieldVO>()
                            .lambda()
                            .eq(AsClassfieldVO::getClassId,classVO.getClassId())
                            .eq(AsClassfieldVO::getFieldId,fieldId));
                }
            }

            List<String> monitorBefore = asClassmonitorDao.queryMonitorIdsByClassId(classVO.getClassId());
            List<String> monitorNow = classManage.getClassMonitor();
            List<String> updateMonitor = null;

            if(monitorNow.size() > monitorBefore.size()){
                updateMonitor = monitorNow.stream().filter(item -> !monitorBefore.contains(item)).collect(Collectors.toList());
                for(String monitor : updateMonitor){
                    AsClassmonitorVO asClassmonitorVO = new AsClassmonitorVO();
                    asClassmonitorVO.setClassId(classVO.getClassId());
                    asClassmonitorVO.setMonitorId(asClassmonitorDao.queryMonitorIdByName(monitor));
                    asClassmonitorDao.insert(asClassmonitorVO);
                }
            }
            if(monitorNow.size() < monitorBefore.size()){
                updateMonitor = monitorBefore.stream().filter(item -> !monitorNow.contains(item)).collect(Collectors.toList());
                for(String monitor : updateMonitor){
                    Integer monitorId = asClassmonitorDao.queryMonitorIdByName(monitor);
                    log.info("monitorId:" + monitorId);
                    log.info("classId:" + classVO.getClassId());
                    asClassmonitorDao.delete(new QueryWrapper<AsClassmonitorVO>()
                            .lambda()
                            .eq(AsClassmonitorVO::getClassId,classVO.getClassId())
                            .eq(AsClassmonitorVO::getMonitorId,monitorId));
                }
            }

            classManageResult.setClassId(classVO.getClassId());
            classManageResult.setClassName(classVO.getClassName());
            classManageResult.setClassGrade(gradeDao.selectGradeNameById(classVO.getGradeId()));
            classManageResult.setClassFields(asClassfieldDao.queryFieldIdsByClassId(classVO.getClassId()));
            classManageResult.setClassMonitor(asClassmonitorDao.queryMonitorIdsByClassId(classVO.getClassId()));

        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }

        return classManageResult;
    }

    @Override
    public ClassManage insertClassMessage(ClassManage classManage) {
        try{
            ClassVO classVO = new ClassVO();
            classVO.setClassName(classManage.getClassName());
            classVO.setGradeId(gradeDao.selectGradeIdByName(classManage.getClassGrade()));
            classDao.insert(classVO);

            Integer classId = classDao.selectOne(new QueryWrapper<ClassVO>()
                    .lambda()
                    .eq(ClassVO::getClassName,classVO.getClassName())
                    .eq(ClassVO::getGradeId,classVO.getGradeId())).getClassId();
            classManage.setClassId(classId);

            List<String> classFields = classManage.getClassFields();
            for(String fields : classFields){
                AsClassfieldVO asClassfieldVO = new AsClassfieldVO();
                asClassfieldVO.setClassId(classId);
                asClassfieldVO.setFieldId(asClassfieldDao.queryFieldIdByName(fields));
                asClassfieldDao.insert(asClassfieldVO);
            }

            List<String> classMonitors = classManage.getClassMonitor();
            for(String monitors : classMonitors){
                AsClassmonitorVO asClassmonitorVO = new AsClassmonitorVO();
                asClassmonitorVO.setClassId(classId);
                asClassmonitorVO.setMonitorId(asClassmonitorDao.queryMonitorIdByName(monitors));
                asClassmonitorDao.insert(asClassmonitorVO);
            }

        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }
        return classManage;
    }
}
