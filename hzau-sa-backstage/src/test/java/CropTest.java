import hzau.sa.backstage.BackStageApplication;
import hzau.sa.backstage.service.impl.CropServiceImpl;
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
        cropService.removeByIds(arrayList);
    }

}
