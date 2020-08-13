package hzau.sa.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.entity.StudentVO;
import hzau.sa.backstage.dao.StudentDao;
import hzau.sa.backstage.entity.StudentVO;
import hzau.sa.backstage.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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

    private static final int size=10;
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
     * 返回所有年级
     */
    @Override
    public Result getAllGrades(){
        QueryWrapper<StudentVO> wrapper = new QueryWrapper<StudentVO>();
        wrapper.select("gradeId");

        List<StudentVO> students= studentDao.selectList(wrapper);
        return ResultUtil.success(students);
    }

    /**
     * 按年级分页
     */
    @Override
    public Result pageByGrade(String gradeId, int pageNo){
        Page<StudentVO> page = new Page<StudentVO>(pageNo,size);

        QueryWrapper<StudentVO> wrapper = new QueryWrapper<>();
        wrapper.like("gradeId",gradeId);

        IPage<StudentVO> iPage = studentDao.selectPage(page, wrapper);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("totalPages",iPage.getPages());
        hashMap.put("totalRecordNums",iPage.getTotal());
        hashMap.put("Records",iPage.getRecords());

        return ResultUtil.success(hashMap);
    }

    /**
     * 返回所有班级
     */
    @Override
    public Result gerAllClasses(){
        QueryWrapper<StudentVO> wrapper = new QueryWrapper<StudentVO>();
        wrapper.select("classId");

        List<StudentVO> students= studentDao.selectList(wrapper);
        return ResultUtil.success(students);
    }

    /**
     * 按班级分页
     */

    @Override
    public Result pageByClasses(String classId, int pageNo){
        Page<StudentVO> page = new Page<StudentVO>(pageNo,size);

        QueryWrapper<StudentVO> wrapper = new QueryWrapper<>();
        wrapper.like("classId",classId);

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
     * 添加学生
     */
    @Override
    public Result addStudent(StudentVO studentVO){
        if (studentDao.insert(studentVO)!=0){
            return ResultUtil.success();
        }
        return ResultUtil.error("增加学生失败");
    }


    /**
     * 删除学生
     */
    @Override
    public Result deleteStudent(String studentId){
        if (studentDao.deleteById(studentId)!=0){
            return ResultUtil.success();
        }
        return ResultUtil.error("删除学生失败");
    }

    /**
     * 更新学生
     */
    @Override
    public Result updateStudent(StudentVO studentVO){
        if (studentDao.updateById(studentVO)!=0){
            return ResultUtil.success();
        }
        return ResultUtil.error("更新失败");

    }
    /**
     * 从模板中添加学生
     */
    @Override
    public Result addStudentByTemplate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        return  ResultUtil.success();
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
