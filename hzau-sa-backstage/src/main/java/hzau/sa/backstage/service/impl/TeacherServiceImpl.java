package hzau.sa.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.StudentVO;
import hzau.sa.backstage.entity.TeacherVO;
import hzau.sa.backstage.dao.TeacherDao;
import hzau.sa.backstage.entity.TeacherVO;
import hzau.sa.backstage.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuyihu
 * @since 2020-08-13
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherDao, TeacherVO> implements TeacherService {

    @Autowired
    private TeacherDao teacherDao;

    private static final int size=10;
    /**
     * 分页显示老师
     */



    @Override
    public Result page(int pageNo) {
        Page<TeacherVO> page = new Page<TeacherVO>(pageNo,size);

        QueryWrapper<TeacherVO> wrapper = new QueryWrapper<>();

        IPage<TeacherVO> iPage = teacherDao.selectPage(page, wrapper);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("totalPages",iPage.getPages());
        hashMap.put("totalRecordNums",iPage.getTotal());
        hashMap.put("Records",iPage.getRecords());

        return ResultUtil.success(hashMap);
    }

    /**
     * 按名字分页老师
     */
    @Override
    public Result pageByName(String name, int pageNo) {
        Page<TeacherVO> page = new Page<TeacherVO>(pageNo,size);

        QueryWrapper<TeacherVO> wrapper = new QueryWrapper<>();
        wrapper.like("teacherName",name);

        IPage<TeacherVO> iPage = teacherDao.selectPage(page, wrapper);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("totalPages",iPage.getPages());
        hashMap.put("totalRecordNums",iPage.getTotal());
        hashMap.put("Records",iPage.getRecords());

        return ResultUtil.success(hashMap);
    }

    /**
     * 增加老师
     */
    @Override
    public Result addTeacher(TeacherVO teacherVO) {
        if (teacherDao.insert(teacherVO)!=0){
            return ResultUtil.success();
        }
        return ResultUtil.error("增加失败");
    }

    /**
     * 删除老师
     */
    @Override
    public Result deleteTeacher(String techerId) {
        if (teacherDao.deleteById(techerId)!=0){
            return ResultUtil.success();
        }
        return ResultUtil.error("删除失败");
    }

    /**
     * 批量删除老师
     */
    @Override
    public Result deleteTeachers(String[] teacherIds) {
        if (teacherDao.deleteBatchIds(Arrays.asList(teacherIds))!=0){
            return ResultUtil.success();
        }
        return ResultUtil.error("批量删除失败");
    }

    /**
     * 更新老师
     */
    @Override
    public Result updateTeacher(TeacherVO teacherVO) {
        if (teacherDao.updateById(teacherVO)!=0){
            return ResultUtil.success();
        }
        return ResultUtil.error("更新失败");
    }
}
