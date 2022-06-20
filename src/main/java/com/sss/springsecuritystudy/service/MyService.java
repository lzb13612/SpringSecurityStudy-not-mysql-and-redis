package com.sss.springsecuritystudy.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lzb
 * create time: 2022/6/20
 * access结合自定义方法实现权限控制
 */
public interface MyService {
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
