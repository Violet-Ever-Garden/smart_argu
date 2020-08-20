package hzau.sa.msg.excel;

import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.*;

/**
 * @author LvHao
 * @Description :
 * @date 2020-08-20 12:59
 */
public class CustomRowWriteHandler implements RowWriteHandler {

    /**
     * 一定将样式设置成全局变量
     * 首行只需要创建一次样式就可以 不然每行都创建一次 数据量大的话会保错
     * 异常信息：The maximum number of Cell Styles was exceeded. You can define up to 64000 style in a .xlsx Workbook
     */
    private CellStyle firstCellStyle;

    /**
     * 列号
     */
    private int count = 0;

    @Override
    public void beforeRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Integer integer, Integer integer1, Boolean aBoolean) {

    }

    @Override
    public void afterRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer integer, Boolean aBoolean) {
        Cell cell = row.createCell(0);
        if (firstCellStyle == null) {
            Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
            firstCellStyle = MyExcelUtil.firstCellStyle(workbook);
        }
        cell.setCellStyle(firstCellStyle);
        //设置列宽  0列   10个字符宽度
        writeSheetHolder.getSheet().setColumnWidth(0, 10 * 256);
        if (row.getRowNum() == 0) {
            cell.setCellValue("序号");
            return;
        }
        cell.setCellValue(++count);
    }

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer integer, Boolean aBoolean) {

    }
}