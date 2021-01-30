package com.assistant.util;

import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class FileUtil {


    public static boolean str2File(String fileStr,String filePath){
        if (CheckUtil.stringIsNull(fileStr)) // 图像数据为空
            return false;

        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(fileStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }

            OutputStream out = new FileOutputStream(filePath);
            out.write(b);
            out.flush();
            out.close();

            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static void main(String[] args) {
        File file = new File("D:\\sign");
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            String names = "";
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                names += "'rise/user/sign/"+files[i].getName()+"',";
            }
            names.substring(0,names.lastIndexOf(',')-1);

            System.out.println(names);
        }
    }

}
