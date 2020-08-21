package hzau.sa.backstage.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.dao.AsTeacherclassDao;
import hzau.sa.backstage.entity.AsTeacherclassVO;
import hzau.sa.backstage.entity.ClassGradeModel;
import hzau.sa.backstage.entity.CropParameterModel;
import hzau.sa.backstage.entity.TeacherClassModel;
import hzau.sa.backstage.service.AsTeacherclassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author haokai
 * @since 2020-08-13
 */
@Service
public class AsTeacherclassServiceImpl extends ServiceImpl<AsTeacherclassDao, AsTeacherclassVO> implements AsTeacherclassService {
    @Autowired
    AsTeacherclassDao asTeacherclassDao;

    @Override
    public IPage<TeacherClassModel> listByTeacherId(Page<TeacherClassModel> page, String teacherId, String keyword, String gradeName) {
        IPage<TeacherClassModel> list = asTeacherclassDao.listByTeacherId(page,teacherId,"%"+keyword+"%",gradeName);
        return list;
    }
    @Override
    public IPage<ClassGradeModel> listClassWithoutTeacher(Page<ClassGradeModel> page,String keyword,String gradeName,String teacherId) {
        IPage<ClassGradeModel> list = asTeacherclassDao.listClassWithoutTeacher(page,"%"+keyword+"%",gradeName,teacherId);
        return list;
    }
    @Override
    public boolean saveList(List<AsTeacherclassVO> asTeacherclassVOs) {
        return saveBatch(asTeacherclassVOs);
    }
}
