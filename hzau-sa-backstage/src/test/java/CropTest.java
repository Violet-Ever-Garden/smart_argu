import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.backstage.BackStageApplication;
import hzau.sa.backstage.dao.ClassDao;
import hzau.sa.backstage.entity.ClassManage;
import hzau.sa.backstage.service.impl.CropServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest(classes = BackStageApplication.class)
public class CropTest {
    @Autowired
    CropServiceImpl cropService;

    @Test
    void deleteList(){
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(3);
    }

}
