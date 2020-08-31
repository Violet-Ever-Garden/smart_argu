package hzau.sa.backstage.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.BackStageApplication;
import hzau.sa.backstage.entity.VideoMonitorModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LvHao
 * @Description :
 * @date 2020-08-30 16:45
 */
@Slf4j
@SpringBootTest(classes = BackStageApplication.class)
class VideoMonitorDaoTest {

    @Resource
    private VideoMonitorDao videoMonitorDao;

    @Test
    void queryAllVideoMonitor() {
        Page<VideoMonitorModel> page = new Page<>();
        List<VideoMonitorModel> videoMonitorModelList = videoMonitorDao.queryAllVideoMonitor(page,
                "测试",
                "测试基地3",
                null);

        for(VideoMonitorModel videoMonitorModel : videoMonitorModelList){
            log.info(videoMonitorModel.toString());
        }
    }
}