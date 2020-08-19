package hzau.sa.msg.util;

import cn.hutool.core.util.StrUtil;
import hzau.sa.msg.enums.FileEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 * @author LvHao
 * @Description : 文件工具类
 * @date 2020-08-14 18:27
 */
@Component
@Slf4j
public class FileUtil {

    private static String LINUX_OS = "linux";
    private static String WINDOWS_OS = "windows";

    @Resource
    private Environment environment;

    private static String FILE_UPLOAD_PATH;
    private static String SERVER_PORT;
    private static String FILE_MAP;
    private static String IP;

    @PostConstruct
    public void setFileUploadPath(){
        FILE_UPLOAD_PATH = environment.getProperty("file.upload.path");
    }
    @PostConstruct
    public void setServerPort(){
        SERVER_PORT = environment.getProperty("server.port");
    }
    @PostConstruct
    public void setFileMap(){
        FILE_MAP = environment.getProperty("file.map");
    }
    @PostConstruct
    public void setIP(){
        IP = environment.getProperty("ip");
    }


    /**
     * 上传文件
     * @param fileEnum 文件目录的枚举类
     * @param fileMsg 文件的信息
     * @param multipartFile 文件
     * @return 返回的上传文件的绝对路径
     * @throws IOException
     */
    public static String uploadFile(FileEnum fileEnum, String fileMsg, MultipartFile multipartFile) throws IOException {
        String fileUploadPath = FILE_UPLOAD_PATH + fileEnum.name() + File.separator + fileMsg + File.separator;

        File fileUploadDIR = new File(fileUploadPath);
        if(!fileUploadDIR.exists()){
            fileUploadDIR.mkdirs();
        }

        File file = null;
        try{
            file = new File(fileUploadPath + getNewFileName(multipartFile));
            multipartFile.transferTo(file);
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }

        log.info(file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    public static List<String> uploadFiles(FileEnum fileEnum, String fileMsg, MultipartFile[] multipartFiles) throws IOException {
        String fileUploadPath = FILE_UPLOAD_PATH + fileEnum.name() + File.separator + fileMsg + File.separator;

        File fileUploadDIR = new File(fileUploadPath);
        if(!fileUploadDIR.exists()){
            fileUploadDIR.mkdirs();
        }

        List<String> filePaths = new ArrayList<>();
        try{
            for(MultipartFile multipartFile : multipartFiles){
                File file = null;
                file = new File(fileUploadPath + getNewFileName(multipartFile));
                multipartFile.transferTo(file);
                filePaths.add(file.getAbsolutePath());
            }
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }

        for(String paths : filePaths){
            log.info(paths);
        }

        return filePaths;
    }

    /**
     * 删除文件
     * @param filePath 文件的绝对路径
     * @return
     */
    public static boolean deleteFile(String filePath){
        boolean flag = false;
        try{
            flag = new File(filePath).delete();
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }
        return flag;
    }

    /**
     * 修改文件  先删除 后添加
     * @param filePath  要修改文件的路径
     * @param multipartFile 新文件
     * @return
     * @throws IOException
     */
    public static String changeFile(String filePath,MultipartFile multipartFile) throws IOException {
        File newFile = null;
        try{
            new File(filePath).delete();

            String changeFilePath = filePath.substring(0,filePath.lastIndexOf(File.separator) + 1);

            File fileUDIR = new File(changeFilePath);
            if(!fileUDIR.exists()){
                fileUDIR.mkdirs();
            }

            newFile = new File(changeFilePath + getNewFileName(multipartFile));
            multipartFile.transferTo(newFile);
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }

        return newFile.getAbsolutePath();
    }

    /**
     * 根据文件路径获取文件的访问URL
     * @param filePath
     * @return
     */
    public static String getFileUrl(String filePath){
        String fileLocalPath = filePath.replace(FILE_UPLOAD_PATH,"");

        if(StrUtil.equals(OSInfo(),WINDOWS_OS)){
            fileLocalPath = fileLocalPath.replace("\\","/");
        }

        try{
            //String address = getIpAddress().getHostAddress();
            String address = IP;
            if(null != address){
                return "http://" + address + ":" + SERVER_PORT + FILE_MAP.replace("**","") + fileLocalPath;
            }else{
                return null;
            }
        }catch (Exception e){
            log.info(e.toString());
            return null;
        }
    }

    /**
     * 获取带有时间戳的文件名
     * @param multipartFile 文件
     * @return
     */
    private static String getNewFileName(MultipartFile multipartFile){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String timeStamp = simpleDateFormat.format(new Date());

        String multipartFileName = multipartFile.getOriginalFilename();
        String fileSuffix = multipartFileName.substring(multipartFileName.lastIndexOf(".") + 1);
        String fileName = multipartFileName.substring(0,multipartFileName.lastIndexOf("."));

        return fileName + "-" + timeStamp + "." + fileSuffix;
    }

    /**
     * 判断系统类型
     * @return
     */
    private static String OSInfo(){
         String OS = System.getProperty("os.name").toLowerCase();

         if(OS.indexOf(LINUX_OS) >= 0){
             return LINUX_OS;
         }else if(OS.indexOf(WINDOWS_OS) >= 0){
             return WINDOWS_OS;
         }

         return null;
    }

    /**
     * 获取本机IP地址
     * @return
     * @throws UnknownHostException
     */
    private static InetAddress getIpAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    // 排除loopback类型地址
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        } catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException(
                    "Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }
}
