<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE html>
<html>
<head>
	<title>商品详情</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link type="text/css" rel="stylesheet" href="css/bootstrap.css">
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/flexslider.css">
	<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.flexslider.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="layer/layer.js"></script>
	<script type="text/javascript" src="js/cart.js"></script>
	<script>
		$(function() {
		  $('.flexslider').flexslider({
			animation: "slide",
			controlNav: "thumbnails"
		  });
		});
	</script>
</head>
<body>
	 
	





	<!--header-->
    <jsp:include page="/header.jsp"></jsp:include>
	<!--//header-->

	
	<!--//single-page-->
	<div class="single">
		<div class="container">
			<div class="single-grids">				
				<div class="col-md-4 single-grid">		
					<div class="flexslider">
						
						<ul class="slides">
							<li data-thumb="${g.cover}">
								<div class="thumb-image"> <img src="${g.cover}" data-imagezoom="true" class="img-responsive"> </div>
							</li>
							<li data-thumb="${g.image1}">
								 <div class="thumb-image"> <img src="${g.image1}" data-imagezoom="true" class="img-responsive"> </div>
							</li>
							<li data-thumb="${g.image2}">
							   <div class="thumb-image"> <img src="${g.image2}" data-imagezoom="true" class="img-responsive"> </div>
							</li> 
						</ul>
					</div>
				</div>	
				<div class="col-md-4 single-grid simpleCart_shelfItem">		
					<h3>${g.name}</h3>
					<div class="tag">
						<p>分类 : <a href="goods_list?typeid=${g.type.id}">${g.type.name}</a></p>
					</div>
					<p>${g.intro}</p>
					<div class="galry">
						<div class="prices">
							<h5 class="item_price">¥ ${g.price}</h5>
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="btn_form">
						<a href="javascript:;" class="add-cart item_add" onclick="buy(${g.id})">加入购物车</a>
						<button type="button" class="btn btn-outline-primary favorite-btn" data-id="${g.id}" style="margin-left: 10px;">
							<i class="fa ${isFavorite ? 'fa-heart' : 'fa-heart-o'}"></i>
							<span>${isFavorite ? '已收藏' : '收藏'}</span>
						</button>
					</div>
				</div>
				<div class="col-md-4 single-grid1">
					<!-- <h2>商品分类</h2> -->
					<ul>
                        <li><a  href="/goods_list">全部系列</a></li>

                        <c:forEach items="${typeList}" var="t">
                            <li><a href="/goods_list?typeid=${t.id}">${t.name}</a></li>
                        </c:forEach>
					</ul>
				</div>
				<div class="clearfix"> </div>
			</div>
		</div>
	</div>
	
	<!--related-products--><!-- 
	<div class="related-products">
		<div class="container">
			<h3>猜你喜欢</h3>
			<div class="product-model-sec single-product-grids">
				<div class="product-grid single-product">
					<a href="single.html">
					<div class="more-product"><span> </span></div>						
					<div class="product-img b-link-stripe b-animate-go  thickbox">
						<img src="images/m1.png" class="img-responsive" alt="">
						<div class="b-wrapper">
						<h4 class="b-animate b-from-left  b-delay03">							
						<button>View</button>
						</h4>
						</div>
					</div>
					</a>					
					<div class="product-info simpleCart_shelfItem">
						<div class="product-info-cust prt_name">
							<h4>Product #1</h4>								
							<span class="item_price">$2000</span>
							<div class="ofr">
							  <p class="pric1"><del>$2300</del></p>
							  <p class="disc">[15% Off]</p>
							</div>
							<div class="clearfix"> </div>
						</div>												
					</div>
				</div>
				<div class="clearfix"> </div>
			</div>
		</div>
	</div>
 -->	<!--related-products-->	
	
	<!-- 评论区域 -->
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="comment-section" style="margin: 30px 0; padding: 20px; background: #fff; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
					<h3 style="margin-bottom: 20px; color: #333;">商品评价</h3>
					
					<!-- 显示错误信息 -->
					<c:if test="${not empty commentError}">
						<div class="alert alert-danger" role="alert">
							${commentError}
						</div>
					</c:if>
					
					<!-- 平均评分显示 -->
					<div class="avg-score" style="margin-bottom: 30px; padding: 15px; background: #f9f9f9; border-radius: 5px;">
						<h4 style="margin-bottom: 10px;">平均评分：${avgScore != null ? String.format("%.1f", avgScore) : "5.0"} 分</h4>
						<div class="stars" style="color: #f0ad4e; font-size: 20px;">
							<c:forEach begin="1" end="5" var="i">
								<i class="fa fa-star${i <= avgScore ? '' : '-o'}"></i>
							</c:forEach>
						</div>
					</div>

					<!-- 评论表单 -->
					<c:if test="${user != null}">
						<div class="comment-form" style="margin-bottom: 30px; padding: 20px; background: #f9f9f9; border-radius: 5px;">
							<h4 style="margin-bottom: 20px;">发表评论</h4>
							<form id="commentForm" method="post" action="/CookieShop/comment" style="margin-bottom: 0;">
								<input type="hidden" name="goodsId" value="${g.id}">
								<div class="form-group">
									<label>评分：</label>
									<select name="score" class="form-control" style="width: 200px;">
										<option value="5">5星 (非常好)</option>
										<option value="4">4星 (很好)</option>
										<option value="3">3星 (一般)</option>
										<option value="2">2星 (差)</option>
										<option value="1">1星 (很差)</option>
									</select>
								</div>
								<div class="form-group">
									<label>评论内容：</label>
									<textarea name="content" class="form-control" rows="4" required placeholder="请输入您的评论内容..."></textarea>
								</div>
								<button type="submit" class="btn btn-primary">提交评论</button>
							</form>
						</div>
					</c:if>
					<c:if test="${user == null}">
						<div class="alert alert-info" role="alert" style="margin-bottom: 30px;">
							请<a href="user_login.jsp" class="alert-link">登录</a>后发表评论
						</div>
					</c:if>

					<!-- 评论列表 -->
					<div class="comment-list">
						<h4 style="margin-bottom: 20px;">全部评论</h4>
						<c:if test="${empty comments}">
							<div class="no-comment" style="text-align: center; padding: 20px; color: #999;">
								暂无评论，快来抢沙发吧！
							</div>
						</c:if>
						<c:forEach items="${comments}" var="comment">
							<div class="comment-item" style="margin-bottom: 20px; padding: 15px; border-bottom: 1px solid #eee;">
								<div class="comment-user" style="margin-bottom: 10px;">
									<span class="username" style="font-weight: bold; color: #333;">${comment.user.username}</span>
									<span class="time" style="color: #999; margin-left: 15px;">
										<fmt:formatDate value="${comment.createTime}" pattern="yyyy-MM-dd HH:mm"/>
									</span>
								</div>
								<div class="stars" style="color: #f0ad4e; margin-bottom: 10px;">
									<c:forEach begin="1" end="5" var="i">
										<i class="fa fa-star${i <= comment.score ? '' : '-o'}"></i>
									</c:forEach>
								</div>
								<div class="content" style="color: #666; line-height: 1.6;">
									${comment.content}
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 评论相关的JavaScript代码 -->
	<script>
	$(function() {
		// 评论表单提交
		$('#commentForm').submit(function(e) {
			e.preventDefault();
			var content = $('textarea[name="content"]').val().trim();
			if (!content) {
				layer.msg('请输入评论内容！', {icon: 2});
				return;
			}
			$.ajax({
				type: 'POST',
				url: '/CookieShop/comment',
				data: $(this).serialize(),
				success: function(response) {
					console.log('评论响应:', response); // 添加调试日志
					if(response === 'success') {
						layer.msg('评论成功！', {icon: 1});
						setTimeout(function() {
							location.reload();
						}, 1000);
					} else if(response === 'login') {
						layer.msg('请先登录！', {icon: 2});
						setTimeout(function() {
							location.href = 'user_login.jsp';
						}, 1000);
					} else {
						layer.msg('评论失败：' + response, {icon: 2}); // 显示具体错误信息
					}
				},
				error: function(xhr, status, error) {
					console.log('评论错误:', error); // 添加调试日志
					console.log('状态:', status);
					console.log('响应:', xhr.responseText);
					layer.msg('系统错误：' + error, {icon: 2}); // 显示具体错误信息
				}
			});
		});
	});
	</script>
	
	<!-- 添加收藏相关的JavaScript代码 -->
	<script>
	function toggleFavorite(goodsid) {
		$.ajax({
			url: '${pageContext.request.contextPath}/favorite',
			type: 'POST',
			data: {
				goodsid: goodsid
			},
			success: function(response) {
				if (response === 'tologin') {
					layer.msg('请先登录!');
					return;
				}
				
				if (response === 'added' || response === 'removed') {
					var $btn = $('.favorite-btn');
					var $icon = $btn.find('i');
					var $text = $btn.find('span');
					
					if (response === 'added') {
						$icon.removeClass('fa-heart-o').addClass('fa-heart');
						$text.text('已收藏');
						layer.msg('收藏成功!');
					} else {
						$icon.removeClass('fa-heart').addClass('fa-heart-o');
						$text.text('收藏');
						layer.msg('取消收藏成功!');
					}
				} else {
					layer.msg('操作失败，请稍后再试！');
				}
			},
			error: function() {
				layer.msg('网络请求失败，请检查您的连接！');
			}
		});
	}
	</script>
	
	




	<!--footer-->
    <jsp:include page="/footer.jsp"></jsp:include>
	<!--//footer-->


</body>
</html>