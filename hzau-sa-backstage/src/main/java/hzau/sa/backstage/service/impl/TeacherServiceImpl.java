package hzau.sa.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.dao.StudentDao;
import hzau.sa.backstage.entity.StudentVO;
import hzau.sa.backstage.entity.TeacherVO;
import hzau.sa.backstage.dao.TeacherDao;
import hzau.sa.backstage.entity.TeacherVO;
import hzau.sa.backstage.entity.TeacherWrapper;
import hzau.sa.backstage.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.msg.dao.FileDao;
import hzau.sa.msg.entity.FileVO;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.FileEnum;
import hzau.sa.msg.util.FileUtil;
import hzau.sa.msg.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private FileDao fileDao;

    /**
     * 增加老师
     * 这里直接定向到默认图片
     */
    @Override
    public Result addTeacher(TeacherWrapper teacherWrapper) {

        //判断该老师对象是否存在(id和手机号)
        QueryWrapper<TeacherVO> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.lambda().eq(TeacherVO::getTeacherId,teacherWrapper.getTeacherId())
                .or()
                .eq(TeacherVO::getPhoneNumber,teacherWrapper.getPhoneNumber());

        List<TeacherVO> teachers = teacherDao.selectList(teacherQueryWrapper);

        //如果存在则失败
        if (!teachers.isEmpty()){
            return ResultUtil.paramError("已存在该老师id或手机号码");
        }

        //判断是否在学生里面存在
        QueryWrapper<StudentVO> studentVOQueryWrapper = new QueryWrapper<>();
        studentVOQueryWrapper.lambda().eq(StudentVO::getStudentId,teacherWrapper.getTeacherId());
        StudentVO studentVO = studentDao.selectOne(studentVOQueryWrapper);
        if (studentVO!=null){
            return ResultUtil.error("与学生id重复");
        }

        //解包装得到老师对象
        TeacherVO teacherVO=new TeacherVO(teacherWrapper);

        //设置创建人与最后创建人
        teacherVO.setCreateUser(teacherVO.getCurrentUserName());
        teacherVO.setLastModifiedUser(teacherVO.getCurrentUserName());

        //插入
        if (teacherDao.insert(teacherVO)==0){
            return ResultUtil.databaseError();
        }

        return ResultUtil.success("若不传入密码,则默认密码为123456");

    }

    /**
     * 删除老师
     */
    @Override
    public Result deleteTeacher(String teacherId) {
        QueryWrapper<TeacherVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TeacherVO::getTeacherId,teacherId);

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
        queryWrapper.lambda().in(TeacherVO::getTeacherId,Arrays.asList(teacherIds));

        if (teacherDao.delete(queryWrapper)!=0){
            return ResultUtil.success();
        }
        return ResultUtil.error("批量删除失败");
    }

    /**
     * 更新老师
     * 图片的更新需要弄一下
     */
    @Override
    public Result updateTeacher(TeacherWrapper teacherWrapper, MultipartFile file) {
        //首先得到该老师
        QueryWrapper<TeacherVO> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.lambda().eq(TeacherVO::getTeacherId,teacherWrapper.getTeacherId());

        TeacherVO teacher = teacherDao.selectOne(teacherQueryWrapper);

        //判断是否存在该老师
        if (teacher==null){
            return ResultUtil.error("老师id错误");
        }

        //将teacherWrapper解包装，得到更新后的老师
        TeacherVO teacherW=new TeacherVO(teacherWrapper);

        //得到更新后老师的id和phoneNumber，与数据库比较
        QueryWrapper<TeacherVO> teacherQueryWrapper1 = new QueryWrapper<>();
        teacherQueryWrapper1.lambda().eq(TeacherVO::getPhoneNumber,teacherW.getPhoneNumber());

        TeacherVO teacher2 = teacherDao.selectOne(teacherQueryWrapper1);
        if (teacher2!=null){
            return ResultUtil.paramError("该手机号码已经存在");
        }

        teacherW.setLastModifiedUser(teacherW.getCurrentUserName());

//        QueryWrapper<TeacherVO> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("teacherId",teacherW.getTeacherId());
//        teacherW.setCreateUser(teacherDao.selectOne(queryWrapper).getCreateUser());

        //老师更新
        if (teacherDao.update(teacherW,teacherQueryWrapper)==0){
            return ResultUtil.databaseError();
        }
        //判断图片更新
        if (file!=null){
            try {
                //1.存储图片
                String filePath = FileUtil.uploadFile(FileEnum.AVATAR, "teacher", file);
            } catch (IOException e) {
                e.printStackTrace();
                return ResultUtil.error("头像存储失败");
            }
        }
        return ResultUtil.success();
    }
}
