package hzau.sa.file.util;

import hzau.sa.msg.util.ZipUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author LvHao
 * @Description :
 * @date 2020-08-20 0:10
 */
public class ZipTest {
    public static void main(String[] args) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\Users\\LvHao\\Desktop\\物联网项目\\test.zip"));
        boolean flag = ZipUtil.zipCompress("C:\\Users\\LvHao\\Desktop\\物联网项目\\zip",fileOutputStream);
        System.out.println(flag);
    }
}
