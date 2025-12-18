CREATE TABLE `book_image` (
                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT '图片ID',
                              `book_id` bigint NOT NULL COMMENT '图书ID',
                              `image_url` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片URL',
                              `image_type` tinyint DEFAULT '1' COMMENT '图片类型：1-封面，2-详情图，3-预览图',
                              `sort_order` int DEFAULT '0' COMMENT '排序号',
                              `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              PRIMARY KEY (`id`),
                              KEY `idx_book_id` (`book_id`),
                              KEY `idx_image_type` (`image_type`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='图书图片表';