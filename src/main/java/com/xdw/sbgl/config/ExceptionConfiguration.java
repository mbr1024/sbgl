package com.xdw.sbgl.config;

/**
 * Description:
 * Author: suyl
 * Create Data: 2023/6/8
 */


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 说明：错误异常拦截处理
 * 作者：FH Admin
 * from fhadmin.cn
 */
@Configuration
@Slf4j
public class ExceptionConfiguration implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());	//返回json
        mv.addObject("message", ex.getMessage());
        mv.addObject("code", 1);
        log.error("统一异常处理捕获到：{}",ex.getMessage());
        return mv;
    }

}
