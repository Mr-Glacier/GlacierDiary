CREATE TABLE `sys_user` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID，主键',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名，唯一',
    `password` VARCHAR(255) NOT NULL COMMENT '密码（加密存储）',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    `role` VARCHAR(50) NOT NULL DEFAULT 'USER' COMMENT '角色（如 ADMIN, USER）',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '用户状态（1: 启用, 0: 禁用）',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_username` (`username`) COMMENT '用户名唯一索引',
    UNIQUE KEY `unique_email` (`email`) COMMENT '邮箱唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台系统用户表';

-- 初始化管理员账号
INSERT INTO `sys_user` (`username`, `password`, `email`, `phone`, `role`, `status`)
VALUES ('admin', '---', 'admin@example.com', '1234567890', 'ADMIN', 1);