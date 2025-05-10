package dao;

import model.Favorite;
import model.Goods;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.DataSourceUtils;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class FavoriteDao {
    private QueryRunner qr = new QueryRunner();

    // 添加收藏
    public void insert(Favorite favorite) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into favorite(user_id,goods_id) values(?,?)";
        r.update(sql, favorite.getUserId(), favorite.getGoodsId());
    }
    
    // 取消收藏
    public void delete(int userId, int goodsId) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "delete from favorite where user_id=? and goods_id=?";
        r.update(sql, userId, goodsId);
    }
    
    // 查询用户是否收藏了某商品
    public boolean isFavorite(int userId, int goodsId) throws SQLException {
        String sql = "select count(*) from favorite where user_id=? and goods_id=?";
        Long count = qr.query(DataSourceUtils.getConnection(), sql, new ScalarHandler<>(), userId, goodsId);
        return count > 0;
    }
    
    // 获取用户的收藏列表
    public List<Favorite> getUserFavorites(int userId) throws SQLException {
        String sql = "select * from favorite where user_id=? order by create_time desc";
        return qr.query(DataSourceUtils.getConnection(), sql, new BeanListHandler<>(Favorite.class), userId);
    }

    public boolean addFavorite(int userId, int goodsId) throws SQLException {
        String sql = "insert into favorite(user_id,goods_id,create_time) values(?,?,?)";
        return qr.update(DataSourceUtils.getConnection(), sql, userId, goodsId, new Date()) > 0;
    }

    public boolean deleteFavorite(int userId, int goodsId) throws SQLException {
        String sql = "delete from favorite where user_id=? and goods_id=?";
        return qr.update(DataSourceUtils.getConnection(), sql, userId, goodsId) > 0;
    }
} 