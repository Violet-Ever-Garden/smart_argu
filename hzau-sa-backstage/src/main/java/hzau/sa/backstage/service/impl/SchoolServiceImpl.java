package hzau.sa.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import hzau.sa.backstage.dao.SchoolDao;
import hzau.sa.backstage.entity.SchoolVO;
import hzau.sa.backstage.service.SchoolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.FileUtil;
import hzau.sa.msg.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * @param schoolVO
     * @return
     */
    @Override
    public Result addSchool(SchoolVO schoolVO){
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
     * @param schoolVO
     * @return
     */
    @Override
    public Result updateSchool(SchoolVO schoolVO){
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
    public Result templateDownload(){
        String fileUrl = FileUtil.getFileUrl(SchoolServiceImpl.TEMPLATE_PATH);
        return ResultUtil.success(fileUrl);
    }

//    /**
//     * 从模板增加学校
//     * @return
//     */
//    @Override
//    public Result addSchoolByTemplate(){
//
//    }
}
