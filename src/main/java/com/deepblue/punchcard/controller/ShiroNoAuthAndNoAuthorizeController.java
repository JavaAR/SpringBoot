package com.deepblue.punchcard.controller;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * shiro未认证和未授权的处理类
 */
@RequestMapping("CustomShiroNotPass")
@RestController
public class ShiroNoAuthAndNoAuthorizeController {
    /**
     * 用户未认证拦截
     */
    @GetMapping("noLogin")
    public void noLogin() {
        throw new UnauthenticatedException("用户未登录，请先登录");
    }

    /**
     * 用户没有权限
     */
    @GetMapping("noAuthorize")
    public void noAuthorize() {
        throw new UnauthorizedException("该用户没有权限");
    }
}
