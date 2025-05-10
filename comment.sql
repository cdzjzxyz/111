CREATE TABLE `comment` (
                           `id` int(11) NOT NULL AUTO_INCREMENT,
                           `content` varchar(255) DEFAULT NULL COMMENT '评论内容',
                           `score` int(11) DEFAULT '5' COMMENT '评分(1-5星)',
                           `user_id` int(11) DEFAULT NULL COMMENT '评论用户',
                           `goods_id` int(11) DEFAULT NULL COMMENT '评论商品',
                           `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
                           PRIMARY KEY (`id`),
                           KEY `fk_comment_user_id` (`user_id`),
                           KEY `fk_comment_goods_id` (`goods_id`),
                           CONSTRAINT `fk_comment_goods_id` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`id`) ON DELETE CASCADE,
                           CONSTRAINT `fk_comment_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
