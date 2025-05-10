package service;

import dao.FavoriteDao;
import model.Favorite;
import java.sql.SQLException;

public class FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDao();

    public boolean isFavorite(int userId, int goodsId) {
        try {
            return favoriteDao.isFavorite(userId, goodsId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addFavorite(int userId, int goodsId) {
        try {
            favoriteDao.addFavorite(userId, goodsId);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeFavorite(int userId, int goodsId) {
        try {
            favoriteDao.removeFavorite(userId, goodsId);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
} 