package com.swpu.uchain.community.controller;

import com.swpu.uchain.community.dto.AddTimeDTO;
import com.swpu.uchain.community.entity.AddInfo;
import com.swpu.uchain.community.form.AddInfoForm;
import com.swpu.uchain.community.service.AddTimeService;
import com.swpu.uchain.community.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author；lzh
 * @Date:2019/8/613:18 Descirption:
 */
@RestController
@RequestMapping("/supp")
public class AddTimeController {

    @Autowired
    private AddTimeService addTimeService;

    @PostMapping("/apply")
    public ResultVO apply(AddInfoForm addInfoForm) {
        return addTimeService.insert(addInfoForm);
    }

}
