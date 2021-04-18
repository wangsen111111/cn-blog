package org.example.filter;

import org.example.model.JSONResponse;
import org.example.util.JSONUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 配置用户统一会话管理的过滤器：匹配所有请求路径
 * 服务端资源：/login不用校验session，其他都要校验，如果不通过，返回401。响应内容随便
 * 前端资源：/jsp/校验session，不通过就重定向到登录页面
 * 其他前端资源：/js/,/static/,/view/ 全部不校验
 */
@WebFilter("/*")   //所有的url都要经过过滤器
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 每次http请求匹配到过滤器路径时，会执行该过滤器的doFilter方法
     * 如果往下执行，是调用filterChain.doFilter(request,response)
     * 否则自行处理响应
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        //获取当前请求的服务路径
        String servletPath = req.getServletPath();  //获取服务路径，包括静态文件资源
        //System.out.println(servletPath);
        //不需要登录允许访问：往下继续调用-->其他前端资源：/js/,/static/,/view/ 全部不校验
        if (servletPath.startsWith("/js/") || servletPath.startsWith("/static/")
                || servletPath.startsWith("/view/") || servletPath.equals("/login")) {
            chain.doFilter(request,response);   //表示直接往下执行，不需要验证session
        } else {
            //获取请求的Session对象，没有就返回null
            HttpSession session = req.getSession(false);
            //验证用户是否登录，如果没有登录，还需要根据前端或后端做不同的处理
            if(session==null||session.getAttribute("user")==null){
                //前端重定向到登录界面
                if (servletPath.startsWith("/jsp/")) {
                    resp.sendRedirect(basePath(req)+"/view/login.html");
                }else{  //后端敏感资源访问且没有登录返回401
                    resp.setStatus(401);
                    resp.setCharacterEncoding("UTF-8");
                    resp.setContentType("application/json");
                    //返回统一的json数据格式
                    JSONResponse json = new JSONResponse();
                    json.setCode("LOG000");
                    json.setMessage("用户没有登录，不允许访问");
                    PrintWriter pw = resp.getWriter();
                    pw.println(JSONUtil.serialize(json));
                    pw.flush();
                    pw.close();
                }
            }else{//敏感资源，但已登录，允许继续执行
                //表示已经登录，就放行
                chain.doFilter(request,response);   //表示直接往下执行，不需要验证session
            }
        }
    }

    /**
     * 根据http请求，动态的获取访问路径（服务路径之前的部分）
     */
    public static String basePath(HttpServletRequest req){
        String scheme = req.getScheme();//http
        String host = req.getServerName();//主机ip或域名
        int port = req.getServerPort(); //服务器端口号
        String contextPath = req.getContextPath();  //应用上下文路径
        return scheme+"://"+host+":"+port+contextPath;
    }

    @Override
    public void destroy() {

    }
}
