package com.swpu.uchain.community.controller;


import com.swpu.uchain.community.enums.ResultEnum;
import com.swpu.uchain.community.service.FileService;
import com.swpu.uchain.community.service.ProBriefService;
import com.swpu.uchain.community.util.FileUtil;
import com.swpu.uchain.community.util.ResultVOUtil;
import com.swpu.uchain.community.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;

/**
 * @author zty
 * @date: 2019/8/14 15:20
 * 描述：
 */
@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/admin")
public class DeleteController {
    @Autowired
    private ProBriefService proBriefService;
    @Autowired
    private FileService fileService;
    @GetMapping("/proBriefDelete")
    public ResultVO deleteByProName(
            @RequestParam("proName") String proName){

        String fileUrl = proBriefService.findUrlByProName(proName);
        log.info(fileUrl);
        File file = new File(fileUrl);
        FileUtil.delAllFile(file);
        log.info("文件夹删除完成");
        fileService.deleteByUrl(fileUrl);
        log.info("file表中数据删除完成");
//        int judge = proBriefService.deleteByProName(proName);
//        if(judge!=0){
//            log.info("probrief表中数据删除成功");
//        }
         int result = proBriefService.deleteByProName(proName);
         if(result != 1){
//             return "删除有误";
             return ResultVOUtil.error(ResultEnum.DELETE_ERROR);
         }else {
             return ResultVOUtil.success();
         }
    }
}
