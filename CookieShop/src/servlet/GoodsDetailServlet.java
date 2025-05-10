package servlet;

import dao.CommentDao;
import model.Comment;
import model.Goods;
import model.User;
import service.GoodsService;
import service.FavoriteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "goods_detail",urlPatterns = "/goods_detail")
public class GoodsDetailServlet extends HttpServlet {

    private GoodsService gService = new GoodsService();
    private CommentDao cDao = new CommentDao();
    private FavoriteService fService = new FavoriteService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Goods g = gService.getGoodsById(id);
            if (g == null) {
                request.setAttribute("msg", "商品不存在！");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
            request.setAttribute("g", g);

            // 获取当前用户
            User user = (User) request.getSession().getAttribute("user");
            
            // 检查是否已收藏
            if (user != null) {
                boolean isFavorite = fService.isFavorite(user.getId(), id);
                request.setAttribute("isFavorite", isFavorite);
            }

            try {
                // 加载评论数据
                List<Comment> comments = cDao.getGoodsComments(id);
                double avgScore = cDao.getGoodsAvgScore(id);
                request.setAttribute("comments", comments);
                request.setAttribute("avgScore", avgScore);
                System.out.println("成功加载评论数据：" + comments.size() + "条评论，平均分：" + avgScore);
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("加载评论数据失败：" + e.getMessage());
                // 不要让评论加载失败影响整个页面显示
                request.setAttribute("comments", null);
                request.setAttribute("avgScore", 5.0);
                request.setAttribute("commentError", "加载评论失败：" + e.getMessage());
            }

            request.getRequestDispatcher("/goods_detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("msg", "商品ID格式错误！");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "系统错误：" + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}