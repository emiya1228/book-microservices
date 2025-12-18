CREATE TABLE `book_tag` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '标签ID',
                            `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标签名称',
                            `color` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT '#409EFF' COMMENT '标签颜色',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='图书标签表';