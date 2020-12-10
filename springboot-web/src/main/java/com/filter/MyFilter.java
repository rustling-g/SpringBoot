package com.filter;

import com.sun.org.apache.regexp.internal.RE;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author gg
 * @create 2020-12-09 下午2:55
 */
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("myFilter 执行");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
