package service;

import dao.FavoriteDao;
import java.sql.SQLException;

public class FavoriteService {
    private FavoriteDao fDao = new FavoriteDao();

    public boolean isFavorite(int userId, int goodsId) throws SQLException {
        return fDao.isFavorite(userId, goodsId);
    }

    public boolean addFavorite(int userId, int goodsId) {
        try {
            return fDao.addFavorite(userId, goodsId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteFavorite(int userId, int goodsId) {
        try {
            return fDao.deleteFavorite(userId, goodsId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
} 