package com.swpu.uchain.community.controller;

import com.swpu.uchain.community.service.SignInService;
import com.swpu.uchain.community.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author；lzh
 * @Date:2019/8/613:41 Descirption:
 */
@RestController
@RequestMapping("/sign")
public class SignInController {

    @Autowired
    private SignInService signInService;

    @PostMapping("/signIn")
    public ResultVO UserSignIn (String userId) {
        return signInService.userSignIn(userId);
    }

    @PostMapping("/signOut")
    public ResultVO UserSignOut (String userId) {
        return signInService.userSignOut(userId);
    }

    @GetMapping("/getSignList")
    public ResultVO getSignList () {
        return signInService.getSignList();
    }
}
