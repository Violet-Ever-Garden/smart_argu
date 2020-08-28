package hzau.sa.backstage.service;

import hzau.sa.msg.entity.Result;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.backstage.dao.SchoolDao;
import hzau.sa.backstage.entity.SchoolVO;

/**
 * school 服务实现类
 * @author lvhao
 * @date 2020-08-25
 */
public interface SchoolService  {
    public Result addSchool(SchoolVO schoolVO);
    public Result deleteSchool(Integer schoolId);
    public Result deleteSchools(Integer[] schoolIds);
    public Result updateSchool(SchoolVO schoolVO);
    public Result templateDownload();
//    public Result addSchoolByTemplate();
}