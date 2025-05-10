# CookieShop 曲奇饼干商城

一个基于Java Web技术栈开发的在线曲奇饼干商城系统。

## 功能特性

- 用户管理
  - 用户注册
  - 用户登录
  - 个人信息管理

- 商品管理
  - 商品分类展示
  - 商品详情查看
  - 商品搜索

- 购物功能
  - 购物车管理
  - 订单管理

- 社区功能
  - 商品评论
  - 商品收藏

## 技术栈

- 后端
  - Java 8
  - Servlet 3.1
  - JSP
  - MySQL 8.0
  - JDBC

- 前端
  - HTML5
  - CSS3
  - JavaScript
  - Bootstrap 4
  - jQuery

## 系统要求

- JDK 1.8+
- MySQL 8.0+
- Tomcat 8.5+

## 安装步骤

1. 配置数据库
   - 创建数据库：`cookieshop`
   - 导入数据库脚本：`CookieShop/sql/cookieshop.sql`

2. 配置数据库连接
   - 修改 `src/utils/DBUtil.java` 中的数据库连接信息：
     ```java
     private static final String URL = "jdbc:mysql://localhost:3306/cookieshop?useSSL=false&serverTimezone=UTC";
     private static final String USERNAME = "your_username";
     private static final String PASSWORD = "your_password";
     ```

3. 部署项目
   - 将项目部署到Tomcat的webapps目录

4. 启动服务
   - 启动Tomcat服务器
   - 访问：`http://localhost:8080/CookieShop`

## 项目结构

```
CookieShop/
├── src/                    # 源代码目录
│   ├── dao/               # 数据访问层
│   ├── model/             # 实体类
│   ├── servlet/           # Servlet控制器
│   └── utils/             # 工具类
├── web/                    # Web资源目录
│   ├── WEB-INF/           # Web配置目录
│   ├── css/               # 样式文件
│   ├── js/                # JavaScript文件
│   └── images/            # 图片资源
└── README.md              # 项目说明文档
```

## 使用说明

1. 用户注册/登录
   - 访问首页，点击"注册"或"登录"
   - 填写相关信息完成注册/登录

2. 浏览商品
   - 在首页查看商品列表
   - 使用分类导航或搜索功能查找商品
   - 点击商品进入详情页

3. 购物流程
   - 在商品详情页选择数量加入购物车
   - 在购物车中确认商品信息
   - 提交订单

4. 社区互动
   - 在商品详情页发表评论
   - 收藏喜欢的商品

## 常见问题

1. 数据库连接失败
   - 检查数据库服务是否启动
   - 验证数据库连接信息是否正确
   - 确认数据库用户权限

2. 图片上传失败
   - 检查上传目录权限
   - 确认文件大小是否超限
   - 验证文件格式是否支持

## 维护说明

- 定期备份数据库
- 及时更新安全补丁
- 监控系统日志
- 定期清理临时文件

## 贡献指南

1. Fork 项目
2. 创建特性分支
3. 提交更改
4. 发起 Pull Request

## 许可证

本项目采用 MIT 许可证 