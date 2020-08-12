package hzau.sa.security.service.impl;

import hzau.sa.security.dao.StudentDao;
import hzau.sa.security.entity.StudentVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author haokai
 * @since 2020-08-10
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentDao, StudentVO>  {
    @Autowired
    StudentDao studentDao;


}
