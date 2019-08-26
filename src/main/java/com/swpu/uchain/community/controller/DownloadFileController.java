package com.swpu.uchain.community.controller;



import com.swpu.uchain.community.enums.ResultEnum;
import com.swpu.uchain.community.exception.FileException;
import com.swpu.uchain.community.service.FileService;
import com.swpu.uchain.community.service.ProBriefService;
import com.swpu.uchain.community.util.ZipUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;


/**
 * @author zty
 * @date: 2019/8/8 12:18
 * 描述：下载一个项目中的所有文件
 * 存文件时每个项目一个文件夹
 * 下载时将改文件夹转存到临时文件夹
 * 然后将该临时文件夹打包成zip文件 一起下载（浏览器一次只能下载一个文件）
 */
@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/admin")
public class DownloadFileController {
    @Autowired
    private FileService fileService;

    @Autowired
    private ProBriefService proBriefService;
    @Value("${myPro.temporaryPath}")
    private String temporaryPath;
    @Value("${myPro.fileUrl}")
    private String fileUrl;

    @GetMapping("/download")
    @ResponseBody
    public String fileDown(HttpServletRequest request, HttpServletResponse response) {


        /**
         *获取前端传来的项目名称
         */
        String proName = request.getParameter("proName");
        log.info("下载的项目为:" + proName);

        /**
         * 1.创建临时文件夹
         */
//        String rootPath = ("D://linshi//" + proName);
        log.info(temporaryPath+ proName);
        File temDir = new File(temporaryPath+ proName);
        if (!temDir.exists()) {
            temDir.mkdirs();
        }

        /**
         * 项目文件存放地址
         */
//        String fileUrl = ("D://upload//uploadmulti//"+proName );

        /**
         * 2.生成需要下载的文件，存放在临时文件夹内
         */
        try {
            ZipUtils.copyDir(fileUrl+proName, temporaryPath+proName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * 3.设置response的header
         */
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=uchainfile.zip");

        /**
         * 4.调用工具类，下载zip压缩包
         */
        try {
            ZipUtils.toZip(temDir.getPath(), response.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * 5.删除临时文件和文件夹
         */
        // 这里我没写递归，直接就这样删除了
        File[] listFiles = temDir.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            listFiles[i].delete();
            log.info("正在删除第"+(i+1)+"个文件");
        }
        temDir.delete();
        return null;
    }
}

