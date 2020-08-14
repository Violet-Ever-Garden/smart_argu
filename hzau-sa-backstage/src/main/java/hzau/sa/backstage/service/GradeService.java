package hzau.sa.backstage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.backstage.dao.GradeDao;
import hzau.sa.backstage.entity.GradeVO;

import java.util.List;

/**
 * grade 服务实现类
 * @author lvhao
 * @date 2020-08-12
 */
public interface GradeService  extends IService<GradeVO> {

    /**
     * 查询所有已存在的班级
     * @return
     */
    List<String> queryAllGrade();
}