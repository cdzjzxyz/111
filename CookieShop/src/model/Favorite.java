package model;

import java.util.Date;

public class Favorite {
    private int id;
    private int userId;
    private int goodsId;
    private Date createTime;
    private Goods goods;  // 关联的商品对象
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getGoodsId() {
        return goodsId;
    }
    
    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Goods getGoods() {
        return goods;
    }
    
    public void setGoods(Goods goods) {
        this.goods = goods;
    }
} 