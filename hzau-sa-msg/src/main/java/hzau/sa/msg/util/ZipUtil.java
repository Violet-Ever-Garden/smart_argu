package hzau.sa.msg.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author LvHao
 * @Description : 压缩成zip的工具类
 * @date 2020-08-19 23:42
 */
@Slf4j
public class ZipUtil {

    private static final int BUFFER_SIZE = 2 * 1024;

    /**
     * 压缩文件下的所有文件
     * @param fileDir 文件目录  压缩完成后把该文件夹下的所有文件删除 该文件夹也一并删除
     * @param outputStream 返回给前端的流  压缩文件传完之后 关闭流
     * @return
     * @throws IOException
     */
    public static boolean zipCompress(String fileDir, OutputStream outputStream) throws IOException {
        long startTime = System.currentTimeMillis();
        ZipOutputStream zipOutputStream = null;
        try{
            zipOutputStream = new ZipOutputStream(outputStream);
            File file = new File(fileDir);
            compress(file,zipOutputStream,file.getName());
            long endTime = System.currentTimeMillis();
            boolean deleteFlag = deleteFile(file);
            log.info("压缩完成，耗时：" + (endTime - startTime) +" ms" + "  DeleteFile:" + deleteFlag);
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }finally {
            if(zipOutputStream != null){
                try{
                    zipOutputStream.close();
                }catch (Exception e){
                    log.error(e.toString());
                    throw e;
                }
            }
            return true;
        }
    }

    /**
     * 压缩
     * @param file
     * @param zipOutputStream
     * @param fileName
     * @throws IOException
     */
    private static void compress(File file,ZipOutputStream zipOutputStream,String fileName) throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        try{
            if(file.isFile()){
                zipOutputStream.putNextEntry(new ZipEntry(fileName));
                int len;
                FileInputStream fileInputStream = new FileInputStream(file);
                while((len = fileInputStream.read(bytes)) != -1){
                    zipOutputStream.write(bytes,0,len);
                }
                zipOutputStream.closeEntry();
                fileInputStream.close();
            }else{
                File[] files = file.listFiles();
                for(File fileInFile : files){
                    compress(fileInFile,zipOutputStream,fileInFile.getName());
                }
            }
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }
    }

    /**
     * 压缩完成后删除目录及目录下的所有文件
     * @param file
     * @return
     */
    private static boolean deleteFile(File file){
        if(!file.exists()){
            return false;
        }

        if(file.isFile()){
            return file.delete();
        }else{
            for(File fileInFile : file.listFiles()){
                deleteFile(fileInFile);
            }
        }

        return file.delete();
    }

}
