CREATE TABLE `book_reservation` (
                                    `id` bigint NOT NULL AUTO_INCREMENT,
                                    `user_id` bigint NOT NULL COMMENT '用户ID',
                                    `book_id` bigint NOT NULL COMMENT '图书ID',
                                    `reserve_date` datetime NOT NULL COMMENT '预约时间',
                                    `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0-等待中，1-可借阅（已通知），2-已借阅，3-已取消，4-已过期',
                                    `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                                    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    PRIMARY KEY (`id`),
                                    UNIQUE KEY `uk_user_book_active` (`user_id`,`book_id`,`status`),
                                    KEY `idx_book_status` (`book_id`,`status`),
                                    KEY `idx_user_status` (`user_id`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='图书预约表';