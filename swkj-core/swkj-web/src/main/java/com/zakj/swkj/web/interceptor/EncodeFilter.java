package com.zakj.swkj.web.interceptor;

import com.zakj.swkj.utils.LoggerUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Servlet Filter implementation class EncodeFilter
 */
public class EncodeFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        req.setCharacterEncoding("utf-8");
        LoggerUtils.info(this,"out-file1-type:"+req.getRequestURL().toString());
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }
}
