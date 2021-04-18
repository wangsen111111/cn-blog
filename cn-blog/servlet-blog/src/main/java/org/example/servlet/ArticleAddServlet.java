package org.example.servlet;

import org.example.dao.ArticleDAO;
import org.example.model.Article;
import org.example.model.User;
import org.example.util.JSONUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/articleAdd")
public class ArticleAddServlet extends AbstractBaseServlet{
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        //请求数据类型是application/json，需要使用输入流获取
        ServletInputStream is = req.getInputStream();
        //反序列化为文章对象
        Article article = JSONUtil.deserialize(is, Article.class);
        article.setUserId(user.getId());
        int num = ArticleDAO.insert(article);
        return null;
    }
}
