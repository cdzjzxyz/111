package dao;

import model.Comment;
import model.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import utils.DataSourceUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommentDao {
    // 添加评论
    public void insert(Comment comment) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into comment(content,score,user_id,goods_id,create_time) values(?,?,?,?,NOW())";
        r.update(sql, comment.getContent(), comment.getScore(), comment.getUserId(), comment.getGoodsId());
    }

    // 获取商品的所有评论
    public List<Comment> getGoodsComments(int goodsId) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select c.*, u.username, u.name from comment c left join user u on c.user_id = u.id where c.goods_id = ? order by c.create_time desc";
        List<Map<String, Object>> mapList = r.query(sql, new MapListHandler(), goodsId);
        List<Comment> commentList = new ArrayList<>();
        
        for (Map<String, Object> map : mapList) {
            Comment comment = new Comment();
            comment.setId((Integer) map.get("id"));
            comment.setContent((String) map.get("content"));
            comment.setScore((Integer) map.get("score"));
            comment.setUserId((Integer) map.get("user_id"));
            comment.setGoodsId((Integer) map.get("goods_id"));
            comment.setCreateTime((java.util.Date) map.get("create_time"));
            
            // 设置用户信息
            User user = new User();
            user.setUsername((String) map.get("username"));
            user.setName((String) map.get("name"));
            comment.setUser(user);
            
            commentList.add(comment);
        }
        
        return commentList;
    }

    // 获取商品的平均评分
    public double getGoodsAvgScore(int goodsId) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select COALESCE(avg(score), 5.0) as avg_score from comment where goods_id = ?";
        Double avgScore = r.query(sql, rs -> {
            if (rs.next()) {
                return rs.getDouble("avg_score");
            }
            return 5.0; // 默认5分
        }, goodsId);
        return avgScore;
    }

    // 获取用户的所有评论
    public List<Comment> getUserComments(int userId) throws SQLException {
        QueryRunner r = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select c.*, g.name as goods_name from comment c left join goods g on c.goods_id = g.id where c.user_id = ? order by c.create_time desc";
        return r.query(sql, new BeanListHandler<Comment>(Comment.class), userId);
    }
} 