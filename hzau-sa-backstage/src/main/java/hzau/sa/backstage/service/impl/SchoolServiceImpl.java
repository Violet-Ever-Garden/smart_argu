package hzau.sa.backstage.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import hzau.sa.backstage.dao.SchoolDao;
import hzau.sa.backstage.entity.SchoolModel;
import hzau.sa.backstage.entity.SchoolVO;
import hzau.sa.backstage.entity.StudentWrapper;
import hzau.sa.backstage.listener.SchoolListener;
import hzau.sa.backstage.listener.StudentListener;
import hzau.sa.backstage.service.SchoolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.FileUtil;
import hzau.sa.msg.util.ResultUtil;
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

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wyh
 * @since 2020-08-25
 */
@Service
public class SchoolServiceImpl extends ServiceImpl<SchoolDao, SchoolVO> implements SchoolService {
    @Autowired
    private SchoolDao schoolDao;

    private static final String TEMPLATE_PATH="D:/root/hzau/file/excelTemplate/schoolTemplate.xlsx";

    /**
     * 增加学校
     * @param schoolModel
     * @return
     */
    @Override
    public Result addSchool(SchoolModel schoolModel){
        SchoolVO schoolVO = new SchoolVO(schoolModel);

        //判断名字重复性
        QueryWrapper<SchoolVO> schoolVOQueryWrapper = new QueryWrapper<>();
        schoolVOQueryWrapper.lambda().eq(SchoolVO::getSchoolName,schoolVO.getSchoolName());
        SchoolVO schoolVOSelect = schoolDao.selectOne(schoolVOQueryWrapper);
        if (schoolVOSelect!=null){
            return ResultUtil.error("该学校名字已存在");
        }

        //插入
        if (schoolDao.insert(schoolVO)==0){
            return ResultUtil.error("学校增加失败");
        }
        return ResultUtil.success();

    }

    /**
     * 删除学校
     * @param schoolId
     * @return
     */
    @Override
    public Result deleteSchool(Integer schoolId){
        if (schoolDao.deleteById(schoolId)==0){
            return ResultUtil.error("删除失败");
        }
        return ResultUtil.success();
    }

    /**
     * 批量删除学校
     * @param schoolIds
     * @return
     */
    @Override
    public Result deleteSchools(Integer[] schoolIds){
        QueryWrapper<SchoolVO> schoolVOQueryWrapper = new QueryWrapper<>();
        schoolVOQueryWrapper.lambda().in(SchoolVO::getSchoolId, Arrays.asList(schoolIds));

        if (schoolDao.delete(schoolVOQueryWrapper)==0){
            return ResultUtil.error("批量删除失败");
        }
        return ResultUtil.success();
    }

    /**
     * 更新学校
     * @param schoolModel
     * @return
     */
    @Override
    public Result updateSchool(SchoolModel schoolModel){
        SchoolVO schoolVO = new SchoolVO(schoolModel);

        //判断名字重复性
        QueryWrapper<SchoolVO> schoolVOQueryWrapper = new QueryWrapper<>();
        schoolVOQueryWrapper.lambda().eq(SchoolVO::getSchoolName,schoolVO.getSchoolName());
        SchoolVO schoolVOSelect = schoolDao.selectOne(schoolVOQueryWrapper);

        if (schoolVOSelect==null){
            if (schoolDao.updateById(schoolVO)==0){
                return ResultUtil.error("更新失败");
            }
        }else {
            if (schoolVOSelect.getSchoolId().equals(schoolVO.getSchoolId())){
                if (schoolDao.updateById(schoolVO)==0){
                    return ResultUtil.error("更新失败");
                }
            }else {
                return ResultUtil.error("学校名字已存在");
            }
        }
        return ResultUtil.success();
    }

    /**
     * 学校模板下载
     * @return
     */
    @Override
    public Result templateDownload(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        //得到对应的文件对象
        String realPath=SchoolServiceImpl.TEMPLATE_PATH;
        File file = new File(realPath);

        if (file.exists()) {
            // 文件存在，完成下载
            try{
                //得到文件输入流
                FileInputStream fis = new FileInputStream(file);

                //永远是下载 设置以附件的形式进行打开下载
                httpServletResponse.setHeader("content-disposition", "attachment;filename="
                        + "schoolTemplate.xlsx");

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
     * 从模板增加学校
     * @param multipartFile
     * @return
     */
    @Override
    public Result addSchoolByTemplate(MultipartFile multipartFile,SchoolServiceImpl schoolService){
        String fileName=multipartFile.getOriginalFilename();
        String suffix=fileName.substring(fileName.lastIndexOf(".")+1);
        if (!suffix.equals("xlsx")){
            ResultUtil.error("文件必须为xlsx文件，文件模板请下载");
        }
        try {
            EasyExcel.read(multipartFile.getInputStream(),SchoolModel.class,new SchoolListener(schoolService)).ignoreEmptyRow(true).doReadAll();
        } catch (IOException e) {
            e.printStackTrace();
            ResultUtil.error("文件导入失败");
        }
        return ResultUtil.success();
    }

    /**
     * 判断学校名字是否存在
     * @param schoolName
     * @return
     */
    public boolean isSchoolExist(String schoolName){
        QueryWrapper<SchoolVO> schoolVOQueryWrapper = new QueryWrapper<>();
        schoolVOQueryWrapper.lambda().eq(SchoolVO::getSchoolName,schoolName);
        SchoolVO schoolVO = schoolDao.selectOne(schoolVOQueryWrapper);
        if (schoolVO==null){
            return false;
        }
        return true;
    }
}
