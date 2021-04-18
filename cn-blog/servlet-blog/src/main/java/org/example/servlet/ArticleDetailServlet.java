package org.example.servlet;

import org.example.dao.ArticleDAO;
import org.example.model.Article;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/articleDetail")
public class ArticleDetailServlet extends AbstractBaseServlet{
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp){
        //根据获取当前文章的id
        String id = req.getParameter("id");
        //根据文章id从数据库中获取文章信息
        //获取了文章id，文章标签，文章内容，然后封装成文章对象返回
        Article article = ArticleDAO.query(Integer.parseInt(id));
        return article;
    }
}
