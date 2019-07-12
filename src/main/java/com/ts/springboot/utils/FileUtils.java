package com.ts.springboot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    //当文件上传
    public static String fileUpload(MultipartFile file) {
        try {
            //判断上传文件是否存在
            if(file.isEmpty()){
                return "上传文件不存在!";
            }
            //文件上传一个是spring限定的大小，一个是我们限定文件上传的大小
            if(file.getSize()>60*1024*1024){
                return "上传文件太大,上传失败!";
            }
            //获取文件名
            String fileName = file.getOriginalFilename();
            logger.info("上传的文件名为："+fileName);
            //获取文件名的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."),fileName.length());
            logger.info("后缀名为："+suffixName);
            //设置文件存储绝对路径
            String filePath = "D:/Users/dalaoyang/Downloads/";
            String path = filePath + fileName;
            File dest = new File(path);
            if(!dest.getParentFile().exists()){
                // 新建文件夹
                dest.getParentFile().mkdirs();
            }
            // 文件写入
            file.transferTo(dest);
            System.out.println(dest.getParentFile()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(file.getName());
        } catch (IOException e) {
            return "文件上传失败";
        }
        return "文件上传成功！";
    }

    public static String batchFilesUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("file");
        String str = "";
        int i = 1;
        for (MultipartFile file: files) {
            str = "第"+i+"文件上传的情况："+FileUtils.fileUpload(file)+str;
            i = i + 1;
        }
        return str;
    }

    public static void copy2FileUsingFileStreams(InputStream input,File dest) {
        System.out.println(dest.getParentFile());
        FileOutputStream output= null;
        try {
            output = new FileOutputStream(dest);
            copyFileUsingFileStreams(input,output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void copyFileUsingFileStreams(InputStream input,OutputStream output){
        try {
            //output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while((bytesRead = input.read(buf))>0){
                output.write(buf,0,bytesRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(input!=null) {
                    input.close();
                }
                if(output!=null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
