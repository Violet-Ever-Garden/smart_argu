package hzau.sa.backstage.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import hzau.sa.backstage.entity.StudentVO;
import hzau.sa.backstage.listener.StudentListener;
import hzau.sa.msg.entity.Result;
import hzau.sa.msg.util.ResultUtil;

import java.io.InputStream;

/**
 * @author wyh17
 * @version 1.0
 * @date 2020/8/14 11:11
 */
public class ExcelUtil {
    public static void autoWrite(InputStream inputStream){
        ExcelReaderBuilder workBook = EasyExcel.read(inputStream, StudentVO.class, new StudentListener());
        workBook.ignoreEmptyRow(true).doReadAll();
    }
}
