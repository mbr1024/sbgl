package com.xdw.sbgl;

/**
 * Description:
 * Author: suyl
 * Create Data: 2023/6/8
 */



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * 说明：错误异常拦截处理
 * 作者：FH Admin
 * from fhadmin.cn
 */
@Configuration
public class ExceptionConfiguration implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());	//返回json
        mv.addObject("message", ex.getMessage());
        mv.addObject("code", 1);
        ex.printStackTrace();
        return mv;
    }

}
