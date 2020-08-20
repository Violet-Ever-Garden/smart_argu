package hzau.sa.msg.excel;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.util.List;

/**
 * @author LvHao
 * @Description :
 * @date 2020-08-20 14:48
 */
@Slf4j
public class MyExcelUtil {

    public static CellStyle firstCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        //设置边框
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        //文字
        Font font = workbook.createFont();
        font.setBold(Boolean.TRUE);
        cellStyle.setFont(font);
        return cellStyle;
    }

    public static void generateExcel(String fileDir, String fileName, Class dataClass, List data){
        try{
            String filePath = fileDir + File.separator + fileName + ".xls";
            EasyExcel.write(filePath,dataClass)
                    .registerWriteHandler(new CustomRowWriteHandler())
                    .sheet().doWrite(data);
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }
    }

}
