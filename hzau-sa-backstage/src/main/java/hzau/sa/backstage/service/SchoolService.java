package hzau.sa.backstage.service;

import hzau.sa.backstage.entity.SchoolModel;
import hzau.sa.backstage.service.impl.SchoolServiceImpl;
import hzau.sa.msg.entity.Result;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.backstage.dao.SchoolDao;
import hzau.sa.backstage.entity.SchoolVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * school 服务实现类
 * @author wyh
 * @date 2020-08-25
 */
public interface SchoolService  {
    public Result addSchool(SchoolModel schoolModel);
    public Result deleteSchool(Integer schoolId);
    public Result deleteSchools(Integer[] schoolIds);
    public Result updateSchool(SchoolModel schoolModel);
    public Result templateDownload(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
    public Result addSchoolByTemplate(MultipartFile multipartFile, SchoolServiceImpl schoolService);
}