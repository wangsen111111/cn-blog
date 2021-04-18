package org.example.servlet;

import org.example.exception.AppException;
import org.example.model.JSONResponse;
import org.example.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class AbstractBaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //设置请求体编码格式
        req.setCharacterEncoding("UTF-8");
        //设置响应体的编码格式
        resp.setCharacterEncoding("UTF-8");
        //设置响应体的数据类型（浏览器要采取什么方式执行）
        resp.setContentType("application/json");

        //Session会话管理：除登录和注册接口，其他都需要登录后访问
        //req.getServletPath()获取请求服务路径;
        //TODO servlet的子类的session操作

        //创建一个响应对象
        JSONResponse jsonResponse = new JSONResponse();
        try {
            //调用子类重写的方法
            Object data = process(req, resp);
            //子类的process方法执行完没有抛异常，说明业务执行成功
            jsonResponse.setSuccess(true);
            jsonResponse.setData(data);

        }catch (Exception e){
            //异常如何处理？JDBC的异常SQLException，JSON处理的异常
            e.printStackTrace();
            //json.setSuccess(false)不用设置了，因为new的时候已经设置了
            String code = "UNKNOWN";
            String s = "未知的错误";
            //判断是不是自定义异常,instanceof的作用是测试它左边的对象是否是它右边的类的实例
            if(e instanceof AppException){
                code = ((AppException)e).getCode();
                s = e.getMessage();
            }
            jsonResponse.setSuccess(false);
            jsonResponse.setCode(code);
            jsonResponse.setMessage(s);
        }

        //给响应消息体中添加内容
        PrintWriter pw = resp.getWriter();
        pw.println(JSONUtil.serialize(jsonResponse));   //最后把json对象序列化然后通过流添加到响应体中，返回
        pw.flush();
        pw.close();
    }

    protected abstract Object process(HttpServletRequest req,
                                    HttpServletResponse resp) throws Exception;
}
