package com.tr.web111.config;


import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


public class CORSFilter implements Filter {

    private final Logger logger = Logger.getLogger(CORSFilter.class);
    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest  request  = (HttpServletRequest) req;

        //允许所有url路径都可以跨域请求
        //response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

        //允许POST，GET,OPTIONS,DELETE的外域请求
        response.setHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS,DELETE");

        //表名在3600秒内，不需要发送预检请求
        response.setHeader("Access-Control-Max-Age","3600");

        //表明允许跨域请求所包含的头
        //response.setHeader("Access-Control-Allow-Headers","host,connection,content-length,accept,origin,x-requested-with,user-agent,content-type,referer,accept-encoding,accept-language,cookie");
        response.setHeader("Access-Control-Allow-Headers", "DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Authorization,SessionToken,Cookie");

        //ajax跨域求情允许传递cookie
        response.setHeader("Access-Control-Allow-Credentials", "true");

        //获取request的头部信息
        Enumeration<String> headers = request.getHeaderNames();
        while(headers.hasMoreElements()){
            String header = headers.nextElement();
            logger.info("header:"+header+"  value:"+request.getHeader(header));
        }

        //获取response的头部信息
        Collection<String> rheaders = response.getHeaderNames();
        for(String header:rheaders){
            logger.info("ResponseHeader:"+header+"    ResponseValue:"+response.getHeader(header));
        }

        //执行目标路径的mothod
        chain.doFilter(req, resp);

    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}