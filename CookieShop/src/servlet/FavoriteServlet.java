package servlet;

import dao.FavoriteDao;
import model.Favorite;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/favorite")
public class FavoriteServlet extends HttpServlet {
    private FavoriteDao fDao = new FavoriteDao();
    
    // 添加或取消收藏
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if(user == null) {
            response.getWriter().print("login");
            return;
        }
        
        String action = request.getParameter("action");
        int goodsId = Integer.parseInt(request.getParameter("goodsId"));
        
        try {
            if("add".equals(action)) {
                Favorite favorite = new Favorite();
                favorite.setUserId(user.getId());
                favorite.setGoodsId(goodsId);
                fDao.insert(favorite);
                response.getWriter().print("success");
            } else if("remove".equals(action)) {
                fDao.delete(user.getId(), goodsId);
                response.getWriter().print("success");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().print("error");
        }
    }
    
    // 获取收藏列表
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if(user == null) {
            response.sendRedirect("user_login.jsp");
            return;
        }
        
        try {
            List<Favorite> favorites = fDao.getUserFavorites(user.getId());
            request.setAttribute("favorites", favorites);
            request.getRequestDispatcher("/user_favorite.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("index");
        }
    }
} 