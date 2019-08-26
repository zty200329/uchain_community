package com.swpu.uchain.community.controller;



import com.swpu.uchain.community.entity.ProBrief;
import com.swpu.uchain.community.enums.ResultEnum;
import com.swpu.uchain.community.form.ProBriefForm;
import com.swpu.uchain.community.service.FileService;
import com.swpu.uchain.community.service.ProBriefService;
import com.swpu.uchain.community.util.ResultVOUtil;
import com.swpu.uchain.community.vo.ResultVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author zty
 * @date: 2019/8/3 12:07
 * 描述：
 */
@RestController
@Slf4j
@RequestMapping("/admin")
@CrossOrigin
@Api(tags = "文件上传接口")
public class FileController {
    @Autowired
    private FileService fileService;

    @Autowired
    private ProBriefService proBriefService;
    /**
     * 上传的根路径
     */
    @Value("${myPro.realPath}")
    private String realPath;


    /**
     * 上传
     * @param proBriefForm
     * @param request
     * @return
     */

    @PostMapping("/upload")
    @CrossOrigin

    public ResultVO handleFileUpload(@Valid ProBriefForm proBriefForm,
                                     BindingResult bindingResult, HttpServletRequest request,
                                     HttpServletResponse response){
        response.setHeader("P3P","CP=CAO PSA OUR");

        if(bindingResult.hasErrors()){
            log.error("参数出错！（注意必填项！）");
            return ResultVOUtil.error(ResultEnum.PARAMETER_ERROR);
        }

        if(proBriefService.isProExist(proBriefForm.getProName())==0){
            log.error("项目存在");
            return ResultVOUtil.error(ResultEnum.PROJECT_EXIST);
        }

        log.info("接收的参数：项目名："+proBriefForm.getProName());
        log.info("接收的参数：学号："+proBriefForm.getUserId());
        log.info("接收的参数：上传时间："+proBriefForm.getUploadTime());
        log.info("接收的参数：文件类型："+proBriefForm.getProTypeId());
        log.info("接收的参数：展示路径："+proBriefForm.getProShow());
        log.info("html内容："+proBriefForm.getContent());
        log.info("多文件上传路径："+ realPath+proBriefForm.getProName());





        File dest = new File(realPath+proBriefForm.getProName());
        //检测目录是否存在
        if (!dest.exists()) {
            dest.mkdirs();
        }


//        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("file");
        List<MultipartFile> files = Arrays.asList(proBriefForm.getFile());
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (MultipartFile multipartFile : files) {
            file = multipartFile;
            if (!file.isEmpty()) {
                try {

                    com.swpu.uchain.community.entity.File mltifile = new com.swpu.uchain.community.entity.File();
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(new File(realPath + proBriefForm.getProName() + File.separator + file.getOriginalFilename())));
                    stream.write(bytes);
                    mltifile.setFileName(file.getOriginalFilename());
                    mltifile.setFileUrl(realPath + proBriefForm.getProName());
                    mltifile.setFileTypeId(proBriefForm.getProTypeId());


                    fileService.insertFile(mltifile);
                    log.info("多文件上传路径：" + realPath + proBriefForm.getProName() + "//" + file.getOriginalFilename());
                    stream.close();

                } catch (Exception e) {
//                    e.printStackTrace();
                    stream = null;
//                    return "上传失败"+i+"=>"+e.getMessage();
                    return ResultVOUtil.error(ResultEnum.PRO_UPLOAD_ERROR);
                }
            } else {
//                return "上传失败"+i+",文件为空";
                return ResultVOUtil.error(ResultEnum.FILE_NOT_EXIST);
            }

        }
        ProBrief proBrief = new ProBrief();
        proBrief.setProName(proBriefForm.getProName());
        proBrief.setProUserId(proBriefForm.getUserId());
        proBrief.setProUploadTime(proBriefForm.getUploadTime());
        proBrief.setProFileUrl(realPath+proBriefForm.getProName());
        proBrief.setProShow(proBriefForm.getProShow());
        proBrief.setProDesc(proBriefForm.getContent());
        proBrief.setProTypeId(proBriefForm.getProTypeId());

        proBriefService.insert(proBrief);
        return ResultVOUtil.success();
    }
}

