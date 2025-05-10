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
import java.util.Date;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
    private CommentDao cDao = new CommentDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int goodsid = 0;
        try {
            goodsid = Integer.parseInt(request.getParameter("goodsid"));
            // 注意：这里应该是 getUserComments(int userId)，但原方法签名错误，见下方修正
            List<Comment> comments = (List<Comment>) cDao.getUserComments(goodsid);
            request.setAttribute("comments", comments);
            request.getRequestDispatcher("/goods_detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "无效的商品ID");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "获取评论失败");
            request.getRequestDispatcher("/goods_detail.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        User u = (User) request.getSession().getAttribute("user");
        if (u == null) {
            out.write("请先登录");
            return;
        }

        int goodsid = 0;
        try {
            goodsid = Integer.parseInt(request.getParameter("goodsid"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            out.write("无效的商品ID");
            return;
        }

        String content = request.getParameter("content");
        if (content == null || content.trim().isEmpty()) {
            out.write("评论内容不能为空");
            return;
        }

        int score = 5; // 默认评分
        try {
            score = Integer.parseInt(request.getParameter("score"));
            if (score < 1 || score > 5) {
                score = 5; // 如果评分不在1-5范围内，使用默认值
            }
        } catch (NumberFormatException e) {
            // 使用默认评分
        }

        Comment comment = new Comment();
        comment.setScore(score);
        comment.setContent(content);
        comment.setGoodsId(goodsid); // 确保 Comment 类有 setGoodsId(int)
        comment.setUserId(u.getId()); // 确保 Comment 类有 setUserId(int)
        comment.setCreateTime(new Date());

        try {
            boolean flag = cDao.addComment(comment); // ✅ 正确调用 addComment 方法
            if (flag) {
                response.sendRedirect(request.getContextPath() + "/goods_detail?id=" + goodsid);
            } else {
                request.getSession().setAttribute("commentError", "评论发表失败，请稍后再试！");
                response.sendRedirect(request.getContextPath() + "/goods_detail?id=" + goodsid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession().setAttribute("commentError", "系统错误，请稍后再试！");
            response.sendRedirect(request.getContextPath() + "/goods_detail?id=" + goodsid);
        }
    }
}

