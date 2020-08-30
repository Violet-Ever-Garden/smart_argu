package hzau.sa.backstage.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.backstage.dao.ClassDao;
import hzau.sa.backstage.dao.GradeDao;
import hzau.sa.backstage.dao.StudentDao;
import hzau.sa.backstage.dao.TeacherDao;
import hzau.sa.backstage.entity.*;
import hzau.sa.backstage.listener.StudentListener;
import hzau.sa.backstage.service.StudentService;
import hzau.sa.msg.dao.FileDao;
import hzau.sa.msg.entity.FileVO;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.FileEnum;
import hzau.sa.msg.util.FileUtil;
import hzau.sa.msg.util.ResultUtil;
import hzau.sa.msg.util.ShiroKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
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
public class StudentServiceImpl extends ServiceImpl<StudentDao, StudentVO> implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private ClassDao classDao;

    @Autowired
    private GradeDao gradeDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private FileDao fileDao;

    private static final String TEMPLATE_EXCEL_PATH="/root/hzau/file/excelTemplate/studentTemplate.xlsx";
    /**
     * 添加学生
     * 这里也需要修改，在file表里面增加一个默认图片的record
     */
    @Override
    public Result addStudent(StudentWrapper studentWrapper){

        //判断学生是否已经存在（id和手机号）
        QueryWrapper<StudentVO> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.lambda().eq(StudentVO::getStudentId,studentWrapper.getStudentId())
                .or()
                .eq(StudentVO::getPhoneNumber,studentWrapper.getPhoneNumber());

        List<StudentVO> students = studentDao.selectList(studentQueryWrapper);
        if (!students.isEmpty()){
            return ResultUtil.paramError("学生id或者号码已经存在");
        }

        //判断是否在老师表里面存在
        QueryWrapper<TeacherVO> teacherVOQueryWrapper=new QueryWrapper<>();
        teacherVOQueryWrapper.lambda().eq(TeacherVO::getTeacherId,studentWrapper.getStudentId());
        TeacherVO teacherVO = teacherDao.selectOne(teacherVOQueryWrapper);
        if (teacherVO!=null){
            return ResultUtil.paramError("与老师id重复");
        }


        //获得年级表中对应的记录
        QueryWrapper<GradeVO> gradeWrapper=new QueryWrapper<>();
        gradeWrapper.lambda().eq(GradeVO::getGradeName, studentWrapper.getGradeName());
        GradeVO gradeVO = gradeDao.selectOne(gradeWrapper);

        //获得班级表中对应的记录
        QueryWrapper<ClassVO> classWrapper= new QueryWrapper<>();
        classWrapper.lambda()
                .eq(ClassVO::getClassName, studentWrapper.getClassName())
                .eq(ClassVO::getGradeId,gradeVO.getGradeId());

        ClassVO classVO = classDao.selectOne(classWrapper);

        if (classVO==null){
            return ResultUtil.paramError("不存在该年级的该班级");
        }

        //解包装得到对应学生实体
        StudentVO studentVO = new StudentVO(studentWrapper);
        studentVO.setClassId(classVO.getClassId());
        studentVO.setGradeId(classVO.getGradeId());

        //学生进行插入
        if (studentDao.insert(studentVO)==0){
            return ResultUtil.databaseError();
        }

        //使用默认图片
        return ResultUtil.success();
    }

    /**
     * 后台更新学生
     */
    @Override
    public Result updateStudentBackstage(StudentWrapper studentWrapper){

        //判断该学生是否存在
        QueryWrapper<StudentVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(StudentVO::getStudentId,studentWrapper.getStudentId());
        StudentVO student = studentDao.selectOne(queryWrapper);
        if (student==null){
            return ResultUtil.paramError("不存在该学生");
        }

        //判断电话号码是否重复
        QueryWrapper<StudentVO> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.lambda().eq(StudentVO::getPhoneNumber,studentWrapper.getPhoneNumber());
        StudentVO student1 = studentDao.selectOne(queryWrapper1);
        if (student1!=null){
            return ResultUtil.paramError("电话号码已存在");
        }

        //获得年级表中对应的记录
        QueryWrapper<GradeVO> gradeWrapper=new QueryWrapper<>();
        gradeWrapper.lambda().eq(GradeVO::getGradeName, studentWrapper.getGradeName());
        GradeVO gradeVO = gradeDao.selectOne(gradeWrapper);

        //获得班级表中对应的记录
        QueryWrapper<ClassVO> classWrapper= new QueryWrapper<>();
        classWrapper.lambda()
                .eq(ClassVO::getClassName, studentWrapper.getClassName())
                .eq(ClassVO::getGradeId,gradeVO.getGradeId());
        ClassVO classVO = classDao.selectOne(classWrapper);

        if (classVO==null){
            return ResultUtil.paramError("不存在该年级的该班级");
        }

        //解包装得到对应学生实体
        StudentVO studentVO = new StudentVO(studentWrapper);
        studentVO.setClassId(classVO.getClassId());
        studentVO.setGradeId(classVO.getGradeId());

        //学生更新
        if (studentDao.update(studentVO,queryWrapper)==0){
            return ResultUtil.error("更新失败");
        }
        return ResultUtil.success();

    }

    /**
     * 账号更新学生
     */
    @Override
    public Result updateStudentAccount(String studentId,String studentName,String oldPassword,String newPassword,MultipartFile file){
        //学生表更新
        QueryWrapper<StudentVO> studentVOQueryWrapper = new QueryWrapper<>();
        studentVOQueryWrapper.lambda().eq(StudentVO::getStudentId,studentId);
        StudentVO studentVOSelect = studentDao.selectOne(studentVOQueryWrapper);
        if (studentVOSelect==null){
            return ResultUtil.error("学生id错误");
        }

        studentVOSelect.setStudentName(studentName);
        if (oldPassword!=null){
            if (studentVOSelect.getPassword().equals(ShiroKit.md5(oldPassword))){
                if (newPassword==null){
                    return ResultUtil.error("新密码不能为空");
                }else {
                    studentVOSelect.setPassword(ShiroKit.md5(newPassword));
                }
            }else {
                return ResultUtil.error("旧密码错误");
            }
        }

        studentVOSelect.setLastModifiedUser(studentVOSelect.getCurrentUserName());
        studentDao.update(studentVOSelect,studentVOQueryWrapper);

        //头像更新
        if (file!=null){
            String absolutePath=null;
            try {
                //1.存储图片
                absolutePath= FileUtil.uploadFile(FileEnum.AVATAR,"student",file);
            } catch (IOException e) {
                e.printStackTrace();
                return ResultUtil.error("头像存储失败");
            }

            String url=FileUtil.getFileUrl(absolutePath);
            //查看图片路径
            QueryWrapper<FileVO> fileVOQueryWrapper = new QueryWrapper<>();
            fileVOQueryWrapper.lambda().eq(FileVO::getFileType,FileEnum.AVATAR).eq(FileVO::getConnectId,studentId);
            FileVO fileVO = fileDao.selectOne(fileVOQueryWrapper);

            if (fileVO!=null){
                //删除
                FileUtil.deleteFile(fileVO.getFileAbsolutePath());
                //更新属性
                fileVO.setFileAbsolutePath(absolutePath);
                fileVO.setUrl(url);
                //更新操作
                if (fileDao.updateById(fileVO)==0){
                    return ResultUtil.error("图片更新失败");
                }
            }else {
                FileVO fileVOInsert=new FileVO();
                fileVOInsert.setFileAbsolutePath(absolutePath);
                fileVOInsert.setUrl(url);
                fileVOInsert.setFileType(String.valueOf(FileEnum.AVATAR));
                fileVOInsert.setConnectId(studentId);
                if(fileDao.insert(fileVOInsert)==0){
                    return ResultUtil.error("图片更新失败");
                }
            }
        }
        return ResultUtil.success();
    }

    /**
     * 删除学生
     */
    @Override
    public Result deleteStudent(String studentId){
        //删除学生
        QueryWrapper<StudentVO> studentVOQueryWrapper = new QueryWrapper<>();
        studentVOQueryWrapper.lambda().eq(StudentVO::getStudentId,studentId);

        if (studentDao.delete(studentVOQueryWrapper)==0){
            return ResultUtil.error("学生删除失败");
        }

        //删除文件
        QueryWrapper<FileVO> fileVOQueryWrapper = new QueryWrapper<>();
        fileVOQueryWrapper.lambda().eq(FileVO::getFileType,String.valueOf(FileEnum.AVATAR))
                .eq(FileVO::getConnectId,studentId);
        if (fileDao.delete(fileVOQueryWrapper)==0){
            return ResultUtil.error("头像删除失败");
        }


        return ResultUtil.success();
    }


    /**
     * 批量删除学生
     */
    @Override
    public Result deleteStudents(String[] studentIds){

        //删除学生
        QueryWrapper<StudentVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(StudentVO::getStudentId,Arrays.asList(studentIds));

        if (studentDao.delete(queryWrapper)==0){
            return ResultUtil.error("批量删除失败");
        }

        //删除文件
        QueryWrapper<FileVO> fileVOQueryWrapper = new QueryWrapper<>();
        fileVOQueryWrapper.lambda().eq(FileVO::getFileType,String.valueOf(FileEnum.AVATAR))
                .in(FileVO::getConnectId,studentIds);

        if (fileDao.delete(fileVOQueryWrapper)==0){
            return ResultUtil.error("文件删除失败");
        }

        return ResultUtil.success();
    }


    /**
     * 从模板中添加学生
     * 图片还要设置一个默认的图片位置
     */
    @Override
    public Result addStudentByTemplate(MultipartFile multipartFile, StudentServiceImpl studentService){
        String fileName=multipartFile.getOriginalFilename();
        String suffix=fileName.substring(fileName.lastIndexOf(".")+1);
        if (!suffix.equals("xlsx")){
            ResultUtil.error("文件必须为xlsx文件，文件模板请下载");
        }
        try {
            EasyExcel.read(multipartFile.getInputStream(), StudentWrapper.class,new StudentListener(studentService)).ignoreEmptyRow(true).doReadAll();
        } catch (IOException e) {
            e.printStackTrace();
            ResultUtil.error("文件导入失败");
        }
        return ResultUtil.success();
    }

    /**
     * 模板下载
     * @return
     */
    @Override
    public Result downloadTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        //得到对应的文件对象
        String realPath= StudentServiceImpl.TEMPLATE_EXCEL_PATH;
        File file = new File(realPath);

        if (file.exists()) {
            // 文件存在，完成下载
            try{
                //得到文件输入流
                FileInputStream fis = new FileInputStream(file);

                //永远是下载 设置以附件的形式进行打开下载
                httpServletResponse.setHeader("content-disposition", "attachment;filename="
                        + "template.xlsx");

                //得到输出流
                OutputStream os = httpServletResponse.getOutputStream();
                int len = -1;
                byte[] b = new byte[1024 * 100];
                while ((len = fis.read(b)) != -1) {
                    os.write(b, 0, len);
                    os.flush();
                }
                os.close();
                fis.close();
                return ResultUtil.success();
            }catch (IOException e) {
                e.printStackTrace();
                return ResultUtil.error("文件下载失败");
            }
        }
        return ResultUtil.error("请求文件不存在");
    }

    /**
     * 后台分页查询
     * @param studentName 模糊姓名
     * @param gradeName 年级名
     * @param className 班级名
     * @return
     */
    @Override
    public IPage<StudentModel> page(Page<StudentModel> page, String studentName, String gradeName, String className){
        IPage<StudentModel> iPage = studentDao.page(page, "%"+studentName+"%", gradeName, className);
        return iPage;
    }
}
