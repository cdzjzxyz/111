package servlet;

import model.User;
import service.FavoriteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/favorite")
public class FavoriteServlet extends HttpServlet {
    private FavoriteService fService = new FavoriteService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        // 检查用户登录状态
        User u = (User) request.getSession().getAttribute("user");
        if (u == null) {
            out.write("tologin");
            return;
        }

        // 获取并验证商品ID
        int goodsid = 0;
        try {
            goodsid = Integer.parseInt(request.getParameter("goodsid"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            out.write("error");
            return;
        }

        try {
            boolean isFavorite = fService.isFavorite(u.getId(), goodsid);
            boolean opSuccess;

            if (isFavorite) {
                opSuccess = fService.deleteFavorite(u.getId(), goodsid);
            } else {
                opSuccess = fService.addFavorite(u.getId(), goodsid);
            }

            if (opSuccess) {
                out.write(isFavorite ? "removed" : "added");
            } else {
                out.write("error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.write("error");
        }
    }
} 