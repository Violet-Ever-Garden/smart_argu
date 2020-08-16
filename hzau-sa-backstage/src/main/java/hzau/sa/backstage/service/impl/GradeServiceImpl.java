package hzau.sa.backstage.service.impl;

import hzau.sa.backstage.dao.GradeDao;
import hzau.sa.backstage.entity.GradeVO;
import hzau.sa.backstage.service.GradeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lvhao
 * @since 2020-08-12
 */
@Service
public class GradeServiceImpl extends ServiceImpl<GradeDao, GradeVO> implements GradeService {

    @Resource
    private GradeDao gradeDao;

    @Override
    public List<String> queryAllGrade() {
        List<String> allGrade = new ArrayList<>();

        List<GradeVO> gradeVOS = gradeDao.selectList(null);
        for(GradeVO gradeVO : gradeVOS){
            allGrade.add(gradeVO.getGradeName());
        }

        return allGrade;
    }
}
