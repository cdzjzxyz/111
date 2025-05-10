package dao;

import model.Favorite;
import model.Goods;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.DataSourceUtils;

import java.sql.SQLException;
import java.util.List;

public class FavoriteDao {
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
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from favorite where user_id = ? and goods_id = ?";
        Long count = r.query(sql, new ScalarHandler<Long>(), userId, goodsId);
        return count != null && count > 0;
    }
    
    // 获取用户的收藏列表
    public List<Favorite> getUserFavorites(int userId) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select f.*, g.name as goods_name, g.price, g.cover from favorite f " +
                    "left join goods g on f.goods_id = g.id " +
                    "where f.user_id = ? order by f.create_time desc";
        return r.query(sql, new BeanListHandler<Favorite>(Favorite.class), userId);
    }

    public void addFavorite(int userId, int goodsId) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into favorite(user_id, goods_id) values(?, ?)";
        r.update(sql, userId, goodsId);
    }

    public void removeFavorite(int userId, int goodsId) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "delete from favorite where user_id = ? and goods_id = ?";
        r.update(sql, userId, goodsId);
    }
} 