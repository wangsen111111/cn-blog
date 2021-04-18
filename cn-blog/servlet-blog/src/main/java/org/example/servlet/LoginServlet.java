package org.example.servlet;

import org.example.dao.LoginDAO;
import org.example.exception.AppException;
import org.example.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends AbstractBaseServlet{
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp){
        //getParameter()获取客户端传送给服务器端的参数值
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = LoginDAO.query(username);
        if(user==null)
            throw new AppException("LOG002","用户不存在");
        if(!user.getPassword().equals(password))
            throw new AppException("LOG003","用户名或密码错误");

        //登录成功，创建session
        //如果没有session，就创建一个session
        HttpSession session = req.getSession(true);
        session.setAttribute("user",user);
        return null;


//        if("abc".equals(username)){//模拟用户名登录校验
////            resp.sendRedirect("jsp/articleList.jsp");
//            return null;
//        }else{
//            throw new AppException("LOG001","用户名密码错误");
//        }

    }
}
