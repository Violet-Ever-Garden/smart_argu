package hzau.sa.backstage.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.xml.fastinfoset.tools.FI_DOM_Or_XML_DOM_SAX_SAXEvent;
import hzau.sa.backstage.dao.ClassDao;
import hzau.sa.backstage.dao.GradeDao;
import hzau.sa.backstage.dao.TeacherDao;
import hzau.sa.backstage.entity.*;
import hzau.sa.backstage.dao.StudentDao;
import hzau.sa.backstage.entity.StudentVO;
import hzau.sa.backstage.listener.StudentListener;
import hzau.sa.backstage.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.msg.dao.FileDao;
import hzau.sa.msg.entity.FileVO;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.enums.FileEnum;
import hzau.sa.msg.util.FileUtil;
import hzau.sa.msg.util.ResultUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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

    private static final String TEMPLATE_EXCEL_PATH="/root/hzau/file/excelTemplate/template.xlsx";
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
            return ResultUtil.error("学生id或者号码已经存在");
        }

        //判断是否在老师表里面存在
        QueryWrapper<TeacherVO> teacherVOQueryWrapper=new QueryWrapper<>();
        teacherVOQueryWrapper.lambda().eq(TeacherVO::getTeacherId,studentWrapper.getStudentId());
        TeacherVO teacherVO = teacherDao.selectOne(teacherVOQueryWrapper);
        if (teacherVO!=null){
            return ResultUtil.error("与老师id重复");
        }

        //获得班级表中对应的记录
        QueryWrapper<ClassVO> classWrapper= new QueryWrapper<>();
        classWrapper.lambda().select(ClassVO::getClassId).eq(ClassVO::getClassName, studentWrapper.getClassName());
        ClassVO classVO = classDao.selectOne(classWrapper);

        //获得年级表中对应的记录
        QueryWrapper<GradeVO> gradeWrapper=new QueryWrapper<>();
        gradeWrapper.lambda().select(GradeVO::getGradeId).eq(GradeVO::getGradeName, studentWrapper.getGradeName());
        GradeVO gradeVO = gradeDao.selectOne(gradeWrapper);

        //解包装得到对应学生实体
        StudentVO studentVO = new StudentVO(studentWrapper);
        studentVO.setClassId(classVO.getClassId());
        studentVO.setGradeId(gradeVO.getGradeId());
        studentVO.setCreateUser(studentVO.getCurrentUserName());
        studentVO.setLastModifiedUser(studentVO.getCurrentUserName());

        //学生进行插入
        if (studentDao.insert(studentVO)==0){
            return ResultUtil.databaseError();
        }

        //插入默认图片
        return ResultUtil.success();
    }

    /**
     * 更新学生
     * 这里更新的话对头像更新要弄一下，判断一下是否更新图片
     */
    @Override
    public Result updateStudent(StudentWrapper studentWrapper,MultipartFile multipartFile){

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

        //获得班级表中对应的记录
        QueryWrapper<ClassVO> classWrapper= new QueryWrapper<>();
        classWrapper.lambda().eq(ClassVO::getClassName, studentWrapper.getClassName());
        ClassVO classVO = classDao.selectOne(classWrapper);

        //获得年级表中对应的记录
        QueryWrapper<GradeVO> gradeWrapper=new QueryWrapper<>();
        gradeWrapper.lambda().eq(GradeVO::getGradeName, studentWrapper.getGradeName());
        GradeVO gradeVO = gradeDao.selectOne(gradeWrapper);

        //解包装得到对应学生实体
        StudentVO studentVO = new StudentVO(studentWrapper);
        studentVO.setClassId(classVO.getClassId());
        studentVO.setGradeId(gradeVO.getGradeId());
        studentVO.setLastModifiedUser(studentVO.getCurrentUserName());

        //学生更新
        if (studentDao.update(studentVO,queryWrapper)==0){
            return ResultUtil.error("更新失败");
        }

        //判断图片操作
        if (multipartFile!=null){
            try {
                //1.存储图片
                String absolutePath= FileUtil.uploadFile(FileEnum.AVATAR,"student",multipartFile);

                //查看图片路径
                QueryWrapper<FileVO> fileVOQueryWrapper = new QueryWrapper<>();
                fileVOQueryWrapper.lambda().eq(FileVO::getFileType,FileEnum.AVATAR).eq(FileVO::getConnectId,studentVO.getStudentId());
                FileVO fileVO = fileDao.selectOne(fileVOQueryWrapper);

                if (fileVO!=null){
                    //删除
                    FileUtil.deleteFile(fileVO.getFileAbsolutePath());

                    //更新属性
                    fileVO.setFileAbsolutePath(absolutePath);
                    fileVO.setUrl(FileUtil.getFileUrl(absolutePath));
                    fileVO.setLastModifiedUser(fileVO.getCurrentUserName());

                    //更新操作
                    if (fileDao.update(fileVO,fileVOQueryWrapper)==0){
                        return ResultUtil.error("图片更新失败");
                    }
                }else {
                    FileVO fileVOInsert=new FileVO();
                    fileVOInsert.setFileAbsolutePath(absolutePath);
                    fileVOInsert.setUrl(FileUtil.getFileUrl(absolutePath));
                    fileVOInsert.setFileType(String.valueOf(FileEnum.AVATAR));
                    fileVOInsert.setConnectId(studentVO.getStudentId());
                    fileVOInsert.setCreateUser(fileVO.getCurrentUserName());
                    fileVOInsert.setLastModifiedUser(fileVO.getCurrentUserName());
                    if(fileDao.insert(fileVOInsert)==0){
                        return ResultUtil.error("图片更新失败");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return ResultUtil.error("头像存储失败");
            }
        }
        return ResultUtil.success();

    }

    /**
     * 删除学生
     */
    @Override
    public Result deleteStudent(String studentId){
        QueryWrapper<StudentVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(StudentVO::getStudentId,studentId);

        StudentVO studentVO = studentDao.selectOne(queryWrapper);
        if (studentVO==null){
            return ResultUtil.paramError("不存在该学生id");
        }

        if (studentDao.delete(queryWrapper)==0){
            return ResultUtil.databaseError();
        }
        return ResultUtil.success();
    }


    /**
     * 批量删除学生
     */
    @Override
    public Result deleteStudents(String[] studentIds){
        QueryWrapper<StudentVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(StudentVO::getStudentId,Arrays.asList(studentIds));

        if (studentDao.delete(queryWrapper)==0){
            return ResultUtil.error("批量删除失败");
        }
        return ResultUtil.success();
    }


    /**
     * 从模板中添加学生
     * 图片还要设置一个默认的图片位置
     */
    @Override
    public Result addStudentByTemplate(MultipartFile multipartFile,StudentServiceImpl studentService){
        String fileName=multipartFile.getOriginalFilename();
        String suffix=fileName.substring(fileName.lastIndexOf(".")+1);
        if (!suffix.equals("xlsx")){
            ResultUtil.error("文件必须为xlsx文件，文件模板请下载");
        }
        try {
            EasyExcel.read(multipartFile.getInputStream(),StudentWrapper.class,new StudentListener(studentService)).ignoreEmptyRow(true).doReadAll();
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
        String realPath=StudentServiceImpl.TEMPLATE_EXCEL_PATH;
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
}
