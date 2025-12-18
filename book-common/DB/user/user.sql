CREATE TABLE `user` (
                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                        `username` varchar(50) NOT NULL COMMENT '用户名',
                        `password` varchar(255) NOT NULL COMMENT '密码（加密后）',
                        `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
                        `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
                        `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
                        `avatar` varchar(500) DEFAULT NULL COMMENT '头像URL',
                        `role` int DEFAULT '0' COMMENT '角色：0-读者，1-管理员',
                        `status` int DEFAULT '1' COMMENT '状态：1-正常，0-禁用',
                        `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
                        `register_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
                        `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `uk_username` (`username`),
                        KEY `idx_role_status` (`role`,`status`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';