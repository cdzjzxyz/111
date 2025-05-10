package servlet;

import dao.CommentDao;
import model.Comment;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
    private CommentDao cDao = new CommentDao();
    
    // 添加评论
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=utf-8");
        PrintWriter out = response.getWriter();
        
        try {
            // 检查用户登录状态
            User user = (User) request.getSession().getAttribute("user");
            if(user == null) {
                out.print("login");
                return;
            }
            
            // 获取并验证参数
            String content = request.getParameter("content");
            if(content == null || content.trim().isEmpty()) {
                out.print("评论内容不能为空");
                return;
            }
            
            String scoreStr = request.getParameter("score");
            String goodsIdStr = request.getParameter("goodsId");
            
            if(scoreStr == null || goodsIdStr == null) {
                out.print("参数不完整");
                return;
            }
            
            int score = Integer.parseInt(scoreStr);
            int goodsId = Integer.parseInt(goodsIdStr);
            
            if(score < 1 || score > 5) {
                out.print("评分必须在1-5之间");
                return;
            }
            
            // 创建评论对象
            Comment comment = new Comment();
            comment.setContent(content.trim());
            comment.setScore(score);
            comment.setUserId(user.getId());
            comment.setGoodsId(goodsId);
            
            // 保存评论
            cDao.insert(comment);
            out.print("success");
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
            out.print("参数格式错误");
        } catch (SQLException e) {
            e.printStackTrace();
            out.print("数据库错误：" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            out.print("系统错误：" + e.getMessage());
        }
    }
    
    // 获取评论列表
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int goodsId = Integer.parseInt(request.getParameter("goodsId"));
            List<Comment> comments = cDao.getGoodsComments(goodsId);
            double avgScore = cDao.getGoodsAvgScore(goodsId);
            request.setAttribute("comments", comments);
            request.setAttribute("avgScore", avgScore);
            request.getRequestDispatcher("/goods_detail.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("/index");
        }
    }
} 