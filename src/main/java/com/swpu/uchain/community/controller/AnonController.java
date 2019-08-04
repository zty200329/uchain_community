package com.swpu.uchain.community.controller;

import com.swpu.uchain.community.form.LoginForm;
import com.swpu.uchain.community.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author hobo
 * @description
 */
@RestController
@RequestMapping("/anon")
@Api(tags = "用户认证接口")
public class AnonController {

    @Autowired
    private UserService userService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public Object login(@Valid LoginForm loginForm, HttpServletResponse response) {
        return userService.login(loginForm, response);
    }

}
