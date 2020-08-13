package hzau.sa.backstage.service.impl;


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

    public List<TeacherClassModel> listByTeacherId(Page<TeacherClassModel> page, String teacherId,String keyword,String gradeName) {
        List<TeacherClassModel> list = asTeacherclassDao.listByTeacherId(page,teacherId,"%"+keyword+"%",gradeName);
        return list;
    }

    public List<ClassGradeModel> listClassWithoutTeacher(String keyword,String gradeName) {
        List<ClassGradeModel> list = asTeacherclassDao.listClassWithoutTeacher("%"+keyword+"%",gradeName);
        return list;
    }

    public boolean saveList(List<AsTeacherclassVO> asTeacherclassVOs) {
        return saveBatch(asTeacherclassVOs);
    }
}
