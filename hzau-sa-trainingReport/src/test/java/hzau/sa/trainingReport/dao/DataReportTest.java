package hzau.sa.trainingReport.dao;

import hzau.sa.trainingReport.TrainingReportApplication;
import hzau.sa.trainingReport.service.impl.DataReportServiceImpl;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@SpringBootTest(classes = TrainingReportApplication.class)
public class DataReportTest {

    @Autowired
    DataReportServiceImpl dataReportService;

    @Test
    void write() throws IOException {
        ArrayList<Integer> objects = new ArrayList<>();
        objects.add(2);
        objects.add(3);
        dataReportService.excelDir(objects,17,"1122334455");
    }
}
