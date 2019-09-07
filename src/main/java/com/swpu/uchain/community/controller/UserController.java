package com.swpu.uchain.community.controller;

import com.swpu.uchain.community.dao.AddInfoMapper;
import com.swpu.uchain.community.form.GetSomeoneTimeForm;
import com.swpu.uchain.community.service.AddTimeService;
import com.swpu.uchain.community.service.SignInService;
import com.swpu.uchain.community.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author；lzh
 * @Date:2019/8/810:44 Descirption:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private SignInService signInService;

    @Autowired
    private AddTimeService addTimeService;

    @GetMapping("/getSelfAnyDay")
    public ResultVO getSelfTimeInfo (GetSomeoneTimeForm getSomeoneTimeForm) {
        return signInService.getSelfTimeInfo(getSomeoneTimeForm.getUserId(), getSomeoneTimeForm.getStartDate(), getSomeoneTimeForm.getEndDate());
    }

    @GetMapping("/getAWeekInfo")
    public ResultVO getAweekInfo ( String userId) {
        return signInService.getOneAWeekInfo(userId);
    }

    @GetMapping("/getAddList")
    public ResultVO getAddList (String userId) {
        return addTimeService.getAddList(userId);
    }
}
