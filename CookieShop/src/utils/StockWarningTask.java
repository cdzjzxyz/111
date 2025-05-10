package utils;

import dao.GoodsDao;
import model.Goods;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@WebListener
public class StockWarningTask implements ServletContextListener {
    private Timer timer;
    public static final int WARNING_THRESHOLD = 5; // 库存预警阈值

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        timer = new Timer(true);
        // 每天检查一次库存
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    checkStock();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 24 * 60 * 60 * 1000);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if(timer != null) {
            timer.cancel();
        }
    }

    private void checkStock() throws SQLException {
        GoodsDao goodsDao = new GoodsDao();
        // 获取总商品数
        int totalCount = goodsDao.getCountOfGoodsByTypeID(0);
        // 获取所有商品（typeID=0 表示获取所有商品）
        List<Goods> goodsList = goodsDao.selectGoodsByTypeID(0, 1, totalCount);

        StringBuilder warningMsg = new StringBuilder("库存预警：\n");
        boolean hasWarning = false;

        for(Goods goods : goodsList) {
            if(goods.getStock() <= WARNING_THRESHOLD) {
                hasWarning = true;
                warningMsg.append(goods.getName())
                        .append(" 当前库存：")
                        .append(goods.getStock())
                        .append("\n");
            }
        }

        // 只有当真的有预警信息时才输出
        if(hasWarning) {
            System.out.println(warningMsg.toString());
            // 这里可以添加发送邮件或其他通知方式
        }
    }
}