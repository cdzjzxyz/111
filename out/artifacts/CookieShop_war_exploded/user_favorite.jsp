<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>我的收藏 - CookieShop</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="page-header">
                    <h1>我的收藏</h1>
                </div>
                
                <div class="favorite-list">
                    <c:forEach items="${favorites}" var="favorite">
                        <div class="favorite-item">
                            <div class="row">
                                <div class="col-md-2">
                                    <img src="${favorite.goods.cover}" class="img-responsive" alt="${favorite.goods.name}">
                                </div>
                                <div class="col-md-7">
                                    <h4><a href="goods_detail?id=${favorite.goodsId}">${favorite.goods.name}</a></h4>
                                    <p class="text-danger">￥${favorite.goods.price}</p>
                                    <p class="text-muted">收藏时间：<fmt:formatDate value="${favorite.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                                </div>
                                <div class="col-md-3">
                                    <button class="btn btn-danger remove-favorite" data-id="${favorite.goodsId}">取消收藏</button>
                                    <a href="goods_detail?id=${favorite.goodsId}" class="btn btn-primary">查看详情</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    
                    <c:if test="${empty favorites}">
                        <div class="text-center">
                            <p>您还没有收藏任何商品</p>
                            <a href="index" class="btn btn-primary">去逛逛</a>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    
    <%@ include file="footer.jsp" %>
    
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script>
        $(function() {
            $('.remove-favorite').click(function() {
                var goodsId = $(this).data('id');
                var $item = $(this).closest('.favorite-item');
                
                $.ajax({
                    type: 'POST',
                    url: 'favorite',
                    data: {
                        action: 'remove',
                        goodsId: goodsId
                    },
                    success: function(response) {
                        if(response === 'success') {
                            $item.fadeOut(function() {
                                $(this).remove();
                                if($('.favorite-item').length === 0) {
                                    location.reload();
                                }
                            });
                        } else if(response === 'login') {
                            location.href = 'user_login.jsp';
                        } else {
                            alert('操作失败，请稍后重试！');
                        }
                    }
                });
            });
        });
    </script>
    
    <style>
    .favorite-item {
        padding: 15px 0;
        border-bottom: 1px solid #eee;
    }
    
    .favorite-item:last-child {
        border-bottom: none;
    }
    
    .favorite-item img {
        max-width: 100px;
    }
    
    .favorite-item .btn {
        margin: 5px;
    }
    </style>
</body>
</html> 