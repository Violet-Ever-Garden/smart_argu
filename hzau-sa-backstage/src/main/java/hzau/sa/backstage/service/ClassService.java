package hzau.sa.backstage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import hzau.sa.backstage.entity.ClassManage;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hzau.sa.backstage.dao.ClassDao;
import hzau.sa.backstage.entity.ClassVO;

import java.util.List;
import java.util.Map;

/**
 * class 服务实现类
 * @author lvhao
 * @date 2020-08-12
 */
public interface ClassService extends IService<ClassVO>{

    /**
     * 分页查询数据
     * 模糊查找
     * @param page
     * @param queryWrapper
     * @return
     */
    Map findClass(Page page, QueryWrapper queryWrapper);

    /**
     * 班级地块的查找
     * @param classId
     * @return
     */
    List<Integer> classFields(Integer classId);

    /**
     * 班级视频监控的查找
     * @param classId
     * @return
     */
    List<Integer> classMonitors(Integer classId);

    /**
     * 更新班级的相关信息
     * @param classManage
     * @return
     */
    boolean updateClassMessage(ClassManage classManage);

    /**
     * 新增班级信息
     * @param classManage
     * @return
     */
    ClassManage insertClassMessage(ClassManage classManage);

    /**
     * 查询所有存在的班级
     * @return
     */
    List<String> queryAllClass();

    public IPage<ClassManage> selectClassManage(Page<ClassManage> page, String className, String gradeName);
}