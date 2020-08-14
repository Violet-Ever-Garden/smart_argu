package hzau.sa.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.StudentVO;
import hzau.sa.backstage.entity.TeacherVO;
import hzau.sa.backstage.dao.TeacherDao;
import hzau.sa.backstage.entity.TeacherVO;
import hzau.sa.backstage.entity.TeacherWrapper;
import hzau.sa.backstage.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
    public Result addTeacher(TeacherWrapper teacherWrapper) {
        //解包装得到老师对象
        TeacherVO teacherVO=new TeacherVO(teacherWrapper);

        //判断该老师对象是否存在(id和手机号)
        QueryWrapper<TeacherVO> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.eq("teacherId",teacherVO.getTeacherId())
                .or()
                .eq("phoneNumber",teacherVO.getPhoneNumber());

        List<TeacherVO> teachers = teacherDao.selectList(teacherQueryWrapper);

        //如果存在则失败
        if (!teachers.isEmpty()){
            return ResultUtil.paramError("已存在该老师id或手机号码");
        }

        //判断type是否为admin或teacher
        if (!(teacherVO.getType().equals("admin") || teacherVO.getType().equals("teacher"))){
            return ResultUtil.error("type不合法");
        }

        //不存在就插入
        if (teacherDao.insert(teacherVO)==0){
            return ResultUtil.databaseError();
        }
        return ResultUtil.success("默认密码为123456");

    }

    /**
     * 删除老师
     */
    @Override
    public Result deleteTeacher(String teacherId) {
        QueryWrapper<TeacherVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacherId",teacherId);

        TeacherVO teacher = teacherDao.selectOne(queryWrapper);

        if (teacher==null){
            return ResultUtil.paramError("想要删除的老师的id不存在");
        }

        if (teacherDao.delete(queryWrapper)==0){
            return ResultUtil.databaseError();
        }
        return ResultUtil.success();
    }

    /**
     * 批量删除老师
     */
    @Override
    public Result deleteTeachers(String[] teacherIds) {
        QueryWrapper<TeacherVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("teacherId",Arrays.asList(teacherIds));

        if (teacherDao.delete(queryWrapper)!=0){
            return ResultUtil.success();
        }
        return ResultUtil.error("批量删除失败");
    }

    /**
     * 更新老师
     */
    @Override
    public Result updateTeacher(TeacherWrapper teacherWrapper) {
        //首先得到该老师
        QueryWrapper<TeacherVO> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.eq("teacherId",teacherWrapper.getTeacherId());

        TeacherVO teacher = teacherDao.selectOne(teacherQueryWrapper);

        //判断是否存在该老师
        if (teacher==null){
            return ResultUtil.error("老师id不对");
        }

        //将teacherWrapper解包装，得到更新后的老师
        TeacherVO teacherW=new TeacherVO(teacherWrapper);
        teacherW.setPassword(teacher.getPassword());

        
        //得到更新后老师的id和phoneNumber，与数据库比较
        QueryWrapper<TeacherVO> teacherQueryWrapper1 = new QueryWrapper<>();
        teacherQueryWrapper1.eq("phoneNumber",teacherW.getPhoneNumber());

        TeacherVO teacher2 = teacherDao.selectOne(teacherQueryWrapper1);
        if (teacher2!=null){
            return ResultUtil.paramError("该手机号码已经存在");
        }

        //判断type是否合法
        if (!(teacherW.getType().equals("admin") || teacherW.getType().equals("teacher"))){
            return ResultUtil.error("type不合法");
        }


        //插入
        if (teacherDao.update(teacherW,teacherQueryWrapper)==0){
            return ResultUtil.databaseError();
        }
        return ResultUtil.success();
    }
}
