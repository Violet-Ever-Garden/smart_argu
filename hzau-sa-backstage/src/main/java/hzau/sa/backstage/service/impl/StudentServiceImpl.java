package hzau.sa.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.dao.ClassDao;
import hzau.sa.backstage.dao.GradeDao;
import hzau.sa.backstage.entity.*;
import hzau.sa.backstage.dao.StudentDao;
import hzau.sa.backstage.entity.StudentVO;
import hzau.sa.backstage.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.backstage.util.ExcelUtil;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
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

    private static final int size=10;
    /**
     * 添加学生
     */
    @Override
    public Result addStudent(StudentWrapper studentWrapper){

        //判断学生是否已经存在（id和手机号）
        QueryWrapper<StudentVO> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("studentId",studentWrapper.getStudentId())
                .or()
                .eq("phoneNumber",studentWrapper.getPhoneNumber());

        List<StudentVO> students = studentDao.selectList(studentQueryWrapper);
        if (!students.isEmpty()){
            return ResultUtil.error("学生id或者号码已经存在");
        }

        //获得班级表中对应的记录
        QueryWrapper<ClassVO> classWrapper= new QueryWrapper<>();
        classWrapper.eq("className", studentWrapper.getClassName());
        ClassVO classVO = classDao.selectOne(classWrapper);

        //获得年级表中对应的记录
        QueryWrapper<GradeVO> gradeWrapper=new QueryWrapper<>();
        gradeWrapper.eq("gradeName", studentWrapper.getGradeName());
        GradeVO gradeVO = gradeDao.selectOne(gradeWrapper);

        //解包装得到对应学生实体
        StudentVO studentVO = new StudentVO(studentWrapper);
        studentVO.setClassId(classVO.getClassId());
        studentVO.setGradeId(gradeVO.getGradeId());

        //进行插入
        if (studentDao.insert(studentVO)==0){
            return ResultUtil.databaseError();
        }

        return ResultUtil.success();
    }

    /**
     * 更新学生
     */
    @Override
    public Result updateStudent(StudentWrapper studentWrapper){

        //判断该学生是否存在
        QueryWrapper<StudentVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentId",studentWrapper.getStudentId());
        StudentVO student = studentDao.selectOne(queryWrapper);
        if (student==null){
            return ResultUtil.paramError("不存在该学生");
        }

        //判断电话号码是否重复
        QueryWrapper<StudentVO> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("phoneNumber",studentWrapper.getPhoneNumber());
        StudentVO student1 = studentDao.selectOne(queryWrapper1);
        if (student1!=null){
            return ResultUtil.paramError("电话号码已存在");
        }

        //获得班级表中对应的记录
        QueryWrapper<ClassVO> classWrapper= new QueryWrapper<>();
        classWrapper.eq("className", studentWrapper.getClassName());
        ClassVO classVO = classDao.selectOne(classWrapper);

        //获得年级表中对应的记录
        QueryWrapper<GradeVO> gradeWrapper=new QueryWrapper<>();
        gradeWrapper.eq("gradeName", studentWrapper.getGradeName());
        GradeVO gradeVO = gradeDao.selectOne(gradeWrapper);

        //解包装得到对应学生实体
        StudentVO studentVO = new StudentVO(studentWrapper);
        studentVO.setClassId(classVO.getClassId());
        studentVO.setGradeId(gradeVO.getGradeId());

        //更新
        if (studentDao.update(studentVO,queryWrapper)==0){
            return ResultUtil.error("更新失败");
        }
        return ResultUtil.success();

    }

    /**
     * 删除学生
     */
    @Override
    public Result deleteStudent(String studentId){
        QueryWrapper<StudentVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentId",studentId);

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
        queryWrapper.in("studentId",Arrays.asList(studentIds));

        if (studentDao.delete(queryWrapper)==0){
            return ResultUtil.error("批量删除失败");
        }
        return ResultUtil.success();
    }

    /**
     * 分页列表
     */
    @Override
    public Result page(int pageNo){
        Page<StudentVO> page = new Page<StudentVO>(pageNo,size);

        QueryWrapper<StudentVO> wrapper = new QueryWrapper<>();

        IPage<StudentVO> iPage = studentDao.selectPage(page, wrapper);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("totalPages",iPage.getPages());
        hashMap.put("totalRecordNums",iPage.getTotal());
        hashMap.put("Records",iPage.getRecords());

        return ResultUtil.success(hashMap);

    }

    /**
     * 按名字查找分页
     */
    @Override
    public Result pageByName(String name, int pageNo){
        Page<StudentVO> page = new Page<StudentVO>(pageNo,size);

        QueryWrapper<StudentVO> wrapper = new QueryWrapper<>();
        wrapper.like("studentName",name);

        IPage<StudentVO> iPage = studentDao.selectPage(page, wrapper);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("totalPages",iPage.getPages());
        hashMap.put("totalRecordNums",iPage.getTotal());
        hashMap.put("Records",iPage.getRecords());

        return ResultUtil.success(hashMap);
    }

    /**
     * 按年级分页
     */
    @Override
    public Result pageByGrade(String gradeName, int pageNo){
        //获得grade的对象
        QueryWrapper<GradeVO> gradeQueryWrapper = new QueryWrapper<>();
        gradeQueryWrapper.eq("gradeName",gradeName);
        GradeVO grade = gradeDao.selectOne(gradeQueryWrapper);

        //获得对应的student对象
        QueryWrapper<StudentVO> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("gradeId",grade.getGradeId());
        Page<StudentVO> page = new Page<>(pageNo,size);

        IPage<StudentVO> iPage=studentDao.selectPage(page,studentQueryWrapper);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("totalPages",iPage.getPages());
        hashMap.put("totalRecordNums",iPage.getTotal());
        hashMap.put("Records",iPage.getRecords());

        return ResultUtil.success(hashMap);
    }

    /**
     * 按班级分页
     */
    @Override
    public Result pageByClasses(String className, int pageNo){
        //获取对应的class对象
        QueryWrapper<ClassVO> classQueryWrapper = new QueryWrapper<>();
        classQueryWrapper.eq("className",className);
        ClassVO classVO = classDao.selectOne(classQueryWrapper);

        //获取对应的student
        QueryWrapper<StudentVO> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("classId",classVO.getClassId());
        Page<StudentVO> page=new Page<>(pageNo,size);
        IPage<StudentVO> iPage=studentDao.selectPage(page,studentQueryWrapper);

        //返回
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("totalPages",iPage.getPages());
        hashMap.put("totalRecordNums",iPage.getTotal());
        hashMap.put("Records",iPage.getRecords());

        return ResultUtil.success(hashMap);
    }


    /**
     * 从模板中添加学生
     */
    @Override
    public Result addStudentByTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        try {
            DiskFileItemFactory factory=new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> fileItems = upload.parseRequest(httpServletRequest);

            for (FileItem item:fileItems){
                    if (!item.isFormField()){
                        InputStream inputStream = item.getInputStream();
                        ExcelUtil.autoWrite(inputStream);
                    }
            }
        } catch (FileUploadException | IOException e) {
            e.printStackTrace();
        }
        return ResultUtil.success();
    }

    /**
     * 模板下载
     */
    @Override
    public Result downloadTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        //获取真实路径
        String realPath=httpServletRequest.getServletContext().getRealPath("templates/test.xlsx");
        //获取文件名
        String fileName=realPath.substring(realPath.lastIndexOf("\\")+1);
        //获取文件流
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(realPath));
            //让浏览器支持下载
            httpServletResponse.setHeader("Content-Disposition", "attachment;"+ URLEncoder.encode(fileName,"utf-8"));
            //获取输出流
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();

            int length=0;
            byte[] bytes=new byte[1024];
            while ((length=fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,length);
            }

            fileInputStream.close();
            outputStream.close();
            return ResultUtil.success();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return ResultUtil.error("下载失败");
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtil.error("下载失败");
        }
    }

}
