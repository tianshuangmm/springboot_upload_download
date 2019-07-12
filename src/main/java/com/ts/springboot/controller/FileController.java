package com.ts.springboot.controller;

import com.ts.springboot.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    @RequestMapping(value = "/upload")
    public String upload(@RequestParam("file")MultipartFile file)  {
        //return FileUtils.fileUpload(file);
        String fileName = file.getOriginalFilename();
        //D:
        String filePath = "D:/Users/dalaoyang/Downloads/";
        String path = filePath + fileName;
        try {
            InputStream inputStream = file.getInputStream();
            FileUtils.copy2FileUsingFileStreams(inputStream,new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "copy完成！";
    }

    @PostMapping("/batch")
    public String handleFileUpload(HttpServletRequest request){
        return FileUtils.batchFilesUpload(request);
    }

    private String batchFilesUpload(MultipartHttpServletRequest request) {
        List<MultipartFile> files = request.getFiles("file");
        String str = null;
        for (MultipartFile file: files) {
            str = FileUtils.fileUpload(file);
        }
        return str;
    }
    public String downloadFile(HttpServletRequest request, HttpServletResponse response){
        //String fileName = ""
        return null;
    }
}

