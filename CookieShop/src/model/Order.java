package model;

import dao.GoodsDao;
import utils.PriceUtils;
import utils.DataSourceUtils;

import java.util.*;
import java.sql.SQLException;

import static utils.StockWarningTask.WARNING_THRESHOLD;

public class Order {
    private int id;
    private float total;//总价
    private int amount;// 商品总数
    private int status;//1未付款/2已付款/3已发货/4已完成
    private int paytype;//1微信/2支付宝/3货到付款
    private String name;
    private String phone;
    private String address;
    private Date datetime;
    private User user;
    private Map<Integer,OrderItem> itemMap = new HashMap<Integer,OrderItem>();
    private List<OrderItem> itemList = new ArrayList<OrderItem>();

    public void setUsername(String username) {
        user = new User();
        user.setUsername(username);
    }
    public void addGoods(Goods g) {
        if(itemMap.containsKey(g.getId())) {
            OrderItem item = itemMap.get(g.getId());
            item.setAmount(item.getAmount()+1);
        }else {
            OrderItem item = new OrderItem(g.getPrice(),1,g,this);
            itemMap.put(g.getId(), item);
        }
        amount++;
        total = PriceUtils.add(total, g.getPrice());
    }

    public List<OrderItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<OrderItem> itemList) {
        this.itemList = itemList;
    }

    public void lessen(int goodsid) {
        if(itemMap.containsKey(goodsid)) {
            OrderItem item = itemMap.get(goodsid);
            item.setAmount(item.getAmount()-1);
            amount--;
            total = PriceUtils.subtract(total, item.getPrice());
            if(item.getAmount()<=0) {
                itemMap.remove(goodsid);
            }
        }
    }
    public void delete(int goodsid)
    {
        if(itemMap.containsKey(goodsid)) {
            OrderItem item = itemMap.get(goodsid);
            total = PriceUtils.subtract(total, item.getAmount()*item.getPrice());
            amount-=item.getAmount();
            itemMap.remove(goodsid);
        }
    }

    public Map<Integer, OrderItem> getItemMap() {
        return itemMap;
    }

    public void setItemMap(Map<Integer, OrderItem> itemMap) {
        this.itemMap = itemMap;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public float getTotal() {
        return total;
    }
    public void setTotal(float total) {
        this.total = total;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getPaytype() {
        return paytype;
    }
    public void setPaytype(int paytype) {
        this.paytype = paytype;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Date getDatetime() {
        return datetime;
    }
    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Order() {
        super();
    }

    private void checkStock() throws SQLException {
        GoodsDao goodsDao = new GoodsDao();
        // 使用 typeID=0 获取所有商品，设置页码为1，页大小设置为一个较大的数（比如1000）确保能获取所有商品
        List<Goods> goodsList = goodsDao.selectGoodsByTypeID(0, 1, 1000);
        
        StringBuilder warningMsg = new StringBuilder("库存预警：\n");
        boolean hasWarning = false;
        
        for(Goods goods : goodsList) {
            if(goods.getStock() <= WARNING_THRESHOLD) {
                hasWarning = true;
                warningMsg.append(goods.getName())
                         .append(" 当前库存：")
                         .append(goods.getStock())
                         .append("\n");
                
                // 这里可以添加发送邮件或其他通知方式
                System.out.println(warningMsg.toString());
            }
        }
    }
}
