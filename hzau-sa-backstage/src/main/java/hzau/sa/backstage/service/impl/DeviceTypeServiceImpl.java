package hzau.sa.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import hzau.sa.backstage.dao.AsDevicetypeearlywarningDao;
import hzau.sa.backstage.dao.DeviceTypeDao;
import hzau.sa.backstage.entity.*;
import hzau.sa.backstage.service.DeviceTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author haokai
 * @since 2020-08-21
 */
@Service
public class DeviceTypeServiceImpl extends ServiceImpl<DeviceTypeDao, DeviceTypeVO> implements DeviceTypeService {
    @Autowired
    DeviceTypeDao deviceTypeDao;

    @Autowired
    AsDevicetypeearlywarningServiceImpl asDevicetypeearlywarningService;

    public List<DeviceTypeIdAndName> selectDeviceTypeModel(String moduleType) {
        return deviceTypeDao.selectDeviceTypeModel(moduleType);
    }

    public boolean insertDeviceType(DeviceTypeModel deviceTypeModel) {
        DeviceTypeVO deviceTypeVO = transform(deviceTypeModel);
        deviceTypeDao.insert(deviceTypeVO);
        if(deviceTypeModel.getEarlyWarningIds()==null
                || deviceTypeModel.getEarlyWarningIds().size()==0){
            return true;
        }
        Integer deviceTypeId = deviceTypeVO.getDeviceTypeId();
        ArrayList<AsDevicetypeearlywarningVO> asDevicetypeearlywarningVOS= new ArrayList<>();
        for(int id : deviceTypeModel.getEarlyWarningIds()){
            AsDevicetypeearlywarningVO asDevicetypeearlywarningVO = new AsDevicetypeearlywarningVO();
            asDevicetypeearlywarningVO.setEarlyWarningId(id);
            asDevicetypeearlywarningVO.setDeviceTypeId(deviceTypeId);
            asDevicetypeearlywarningVOS.add(asDevicetypeearlywarningVO);
        }
        asDevicetypeearlywarningService.saveBatch(asDevicetypeearlywarningVOS);
        return true;
    }

    public boolean update(DeviceTypeModel deviceTypeModel) {
        DeviceTypeVO deviceTypeVO = transform(deviceTypeModel);
        deviceTypeDao.updateById(deviceTypeVO);

        Integer deviceTypeId = deviceTypeVO.getDeviceTypeId();
        //查询原本这个设备类型有哪些预警，与当前比较 多的删除掉，少的插入
        QueryWrapper<AsDevicetypeearlywarningVO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(AsDevicetypeearlywarningVO::getDeviceTypeId,deviceTypeId);
        List<AsDevicetypeearlywarningVO> list = asDevicetypeearlywarningService.list(wrapper);
        //需要删除的关系的id，此id为asDevicetypeearlywarningid
        List<Integer> deleteList = new ArrayList<>();
        //需要增加关系的id，此id为earlyWarningId
        List<Integer> insert = new ArrayList<>();
        //搜索需要新增的数据
        for(int id : deviceTypeModel.getEarlyWarningIds()){
            int w = 0;
            for(AsDevicetypeearlywarningVO asDevicetypeearlywarningVO:list){
                if(asDevicetypeearlywarningVO.getEarlyWarningId()==id){
                    w=1;
                    break;
                }
            }
            if(w==0){
                insert.add(id);
            }
        }
        //搜索需要删除的数据
        for(AsDevicetypeearlywarningVO asDevicetypeearlywarningVO:list){
            int w = 0;
            for(int id :deviceTypeModel.getEarlyWarningIds() ){
                if(asDevicetypeearlywarningVO.getEarlyWarningId()==id){
                    w=1;
                    break;
                }
            }
            if(w==0){
                deleteList.add(asDevicetypeearlywarningVO.getAsDevicetypeearlywarningId());
            }
        }
        //删除设备与预警的关系
        for (int id :deleteList){
            asDevicetypeearlywarningService.removeById(id);
        }
        //asDevicetypeearlywarningService.removeByIds(deleteList);

        ArrayList<AsDevicetypeearlywarningVO> asDevicetypeearlywarningVOS= new ArrayList<>();
        for(int id : insert){
            AsDevicetypeearlywarningVO asDevicetypeearlywarningVO = new AsDevicetypeearlywarningVO();
            asDevicetypeearlywarningVO.setEarlyWarningId(id);
            asDevicetypeearlywarningVO.setDeviceTypeId(deviceTypeId);
            asDevicetypeearlywarningVOS.add(asDevicetypeearlywarningVO);
        }
        asDevicetypeearlywarningService.saveBatch(asDevicetypeearlywarningVOS);
        return true;
    }



    public DeviceTypeVO transform(DeviceTypeModel deviceTypeModel){
        DeviceTypeVO deviceTypeVO = new DeviceTypeVO();
        deviceTypeVO.setDeviceTypeId(deviceTypeModel.getDeviceTypeId());
        deviceTypeVO.setDeviceTypeName(deviceTypeModel.getDeviceTypeName());
        deviceTypeVO.setDeviceTypeUnit(deviceTypeModel.getDeviceTypeUnit());
        deviceTypeVO.setTypeCode(deviceTypeModel.getTypeCode());
        deviceTypeVO.setModuleType(deviceTypeModel.getModuleType());
        deviceTypeVO.setMaxWarning(deviceTypeModel.getMaxWarning());
        deviceTypeVO.setMinWarning(deviceTypeModel.getMinWarning());
        return deviceTypeVO;
    }

    public List<EarlyWarningModel> selectEarlyWarningByDeviceTypeId(int deviceTypeId) {
        return deviceTypeDao.selectEarlyWarningByDeviceTypeId(deviceTypeId);
    }
}