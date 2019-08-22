package com.zakj.swkj.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/10/15 0015
 * Time: 0:58
 * Description:
 **/
public class FileUtils {

    /**
     * @param dirRealPath
     * @param file
     * @return
     */
    public static void saveImg(String dirRealPath, File file, String fileName) throws IOException {
        byte[] buffer = new byte[1024];
        //读取文件
        FileInputStream fis = null;
        FileOutputStream fos = null;
        fis = new FileInputStream(file);

        //保存到服务器的文件
        File newFile = new File(dirRealPath + "/" + fileName);
        //如果出现文件重名，则重新上传（重名几率几乎忽略不计）。
        if (newFile.exists()) {
            throw new RuntimeException("上传失败，请重新上传!");
        } else {
            while (!newFile.createNewFile()) {
                if (!newFile.mkdirs()) {
                    throw new RuntimeException("无法保存图片，请重试或联系后台管理员!");
                }
            }
        }
        fos = new FileOutputStream(newFile);
        int length;
        while ((length = fis.read(buffer)) > 0) {
            //每次写入length长度的内容
            fos.write(buffer, 0, length);
        }
        fos.flush();
        //回收资源
        CloseUtils.closeIO(fis, fos);
    }

}
