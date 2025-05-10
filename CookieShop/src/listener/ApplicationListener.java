package listener;

import service.TypeService;
import utils.DataSourceUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebListener()
public class ApplicationListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {
    TypeService tsService = new TypeService();
    // Public constructor is required by servlet spec
    public ApplicationListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("应用程序启动，开始检查数据库...");
        checkDatabase();
        sce.getServletContext().setAttribute("typeList", tsService.GetAllType());
    }

    private void checkDatabase() {
        try {
            Connection conn = DataSourceUtils.getConnection();
            DatabaseMetaData metaData = conn.getMetaData();
            
            // 检查comment表是否存在
            ResultSet tables = metaData.getTables(null, null, "comment", null);
            if (!tables.next()) {
                // 如果表不存在，创建表
                String createCommentTableSQL = 
                    "CREATE TABLE `comment` (" +
                    "`id` int(11) NOT NULL AUTO_INCREMENT," +
                    "`content` varchar(255) DEFAULT NULL COMMENT '评论内容'," +
                    "`score` int(11) DEFAULT '5' COMMENT '评分(1-5星)'," +
                    "`user_id` int(11) DEFAULT NULL COMMENT '评论用户'," +
                    "`goods_id` int(11) DEFAULT NULL COMMENT '评论商品'," +
                    "`create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间'," +
                    "PRIMARY KEY (`id`)," +
                    "KEY `fk_comment_user_id` (`user_id`)," +
                    "KEY `fk_comment_goods_id` (`goods_id`)," +
                    "CONSTRAINT `fk_comment_goods_id` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`id`) ON DELETE CASCADE," +
                    "CONSTRAINT `fk_comment_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
                
                conn.createStatement().execute(createCommentTableSQL);
                System.out.println("评论表创建成功！");
            } else {
                System.out.println("评论表已存在！");
            }

            // 检查favorite表是否存在
            tables = metaData.getTables(null, null, "favorite", null);
            if (!tables.next()) {
                // 如果表不存在，创建表
                String createFavoriteTableSQL = 
                    "CREATE TABLE `favorite` (" +
                    "`id` int(11) NOT NULL AUTO_INCREMENT," +
                    "`user_id` int(11) NOT NULL COMMENT '用户ID'," +
                    "`goods_id` int(11) NOT NULL COMMENT '商品ID'," +
                    "`create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间'," +
                    "PRIMARY KEY (`id`)," +
                    "UNIQUE KEY `uk_user_goods` (`user_id`,`goods_id`)," +
                    "KEY `fk_favorite_goods_id` (`goods_id`)," +
                    "CONSTRAINT `fk_favorite_goods_id` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`id`) ON DELETE CASCADE," +
                    "CONSTRAINT `fk_favorite_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
                
                conn.createStatement().execute(createFavoriteTableSQL);
                System.out.println("收藏表创建成功！");
            } else {
                System.out.println("收藏表已存在！");
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("数据库检查失败：" + e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 应用程序关闭时的清理工作
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute 
         is added to a session.
      */
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attibute
         is replaced in a session.
      */
    }
}
