package com.swpu.uchain.community.controller;

import com.sun.deploy.net.URLEncoder;
import com.swpu.uchain.community.dto.AddTimeDTO;
import com.swpu.uchain.community.dto.ExcelInfoDTO;
import com.swpu.uchain.community.dto.SetQualifyTimeDTO;
import com.swpu.uchain.community.service.AddTimeService;
import com.swpu.uchain.community.service.ExcelService;
import com.swpu.uchain.community.service.SignInService;
import com.swpu.uchain.community.service.UserService;
import com.swpu.uchain.community.util.DateUtil;
import com.swpu.uchain.community.util.ResultVOUtil;
import com.swpu.uchain.community.vo.ResultVO;
import com.swpu.uchain.community.vo.SomeOneTimeVo;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author；lzh
 * @Date:2019/8/820:12 Descirption:
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SignInService signInService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddTimeService addTimeService;

    @Autowired
    private ExcelService excelService;

    /**
     * 管理员获取所有人一周的打卡情况
     * @return
     */
    @GetMapping("/getWeekInfo")
    public ResultVO getAllWeekInfo() {
        String startDate = DateUtil.getMonDay();
        String endDate = DateUtil.getSunDay();
        return signInService.getAllTimeByDates(startDate,endDate);
    }

    /**
     * 管理员查看某人在指定时间的打卡情况
     * @param userName
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/getOneTime")
    public ResultVO getOneTime(String userName, String startDate, String endDate) {
        SomeOneTimeVo someOneTimeVo = signInService.getOneTime(startDate, endDate, userName);
        return ResultVOUtil.success(someOneTimeVo);
    }

    /**
     * 管理员动态设置队员一周打卡时间
     * @param setQualifyTimeDTO
     * @return
     */
    @PostMapping("/setQualifyTime")
    public ResultVO setQualifyTime(SetQualifyTimeDTO setQualifyTimeDTO) {
        return userService.setQualifyTime(setQualifyTimeDTO.getUserName(), setQualifyTimeDTO.getQualifyTime());
    }

    /**
     * 管理员对补签申请进行审核
     * @param addTimeDTO
     * @return
     */
    @PostMapping("/checkApplication")
    public ResultVO check(AddTimeDTO addTimeDTO) {
        return addTimeService.check(addTimeDTO);
    }

    /**
     * 管理员获取本周所有的补签申请
     * @return
     */
    @GetMapping("/getAllAdd")
    public ResultVO getAllAdd() {
        return addTimeService.getAllAddInfo();
    }

    /**
     * 管理员获取指定一段时间所有人的打卡时间
     */
    @GetMapping("/getAllTime")
    public ResultVO getAllTime(String startDate, String endDate) {
        return signInService.getAllTimeByDates(startDate, endDate);
    }

    @GetMapping("/UserExcelDownload")
    public void downloadTimeInfo(HttpServletResponse response) throws UnsupportedEncodingException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("周签到信息表");
        List<ExcelInfoDTO> excelInfoDTOList = excelService.transferExcel();

        /** 设置导出文件的名字*/
        String fileName = "WeekTimeInfo" + ".xls";

        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = {"姓名" ,"本周打卡时间", "本周打卡平均时长", "是否合格"};//headers表示excel表中第一行的表头

        HSSFRow row = sheet.createRow(0);//在excel表中添加表头

        for(int i=0; i<headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        /** 在表中存放查询到的数据放入对应的列*/
        for(ExcelInfoDTO excelInfoDTO: excelInfoDTOList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(excelInfoDTO.getUserName());
            row1.createCell(1).setCellValue(excelInfoDTO.getTotalTime());
            row1.createCell(2).setCellValue(excelInfoDTO.getAverageTime());
            row1.createCell(3).setCellValue(excelInfoDTO.getIsQualify());
            rowNum++;
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.addHeader("Content-Disposition", "attachment;filename =" + URLEncoder.encode(fileName, "utf-8")+ ".xls");
        try {
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
