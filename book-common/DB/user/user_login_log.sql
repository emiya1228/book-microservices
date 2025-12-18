CREATE TABLE `user_login_log` (
                                  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
                                  `user_id` bigint NOT NULL COMMENT '用户ID',
                                  `login_ip` varchar(50) DEFAULT NULL COMMENT '登录IP',
                                  `login_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
                                  `status` tinyint DEFAULT '1' COMMENT '登录状态：0-失败，1-成功',
                                  `message` varchar(200) DEFAULT NULL COMMENT '提示消息',
                                  PRIMARY KEY (`id`),
                                  KEY `idx_user_id` (`user_id`),
                                  KEY `idx_login_time` (`login_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户登录日志表';