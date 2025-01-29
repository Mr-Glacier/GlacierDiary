# 系统用户表设计
CREATE TABLE sys_user
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    account      VARCHAR(50)  NOT NULL,
    user_name    VARCHAR(50)  NOT NULL, -- 名字，不允许为空
    pass_word    VARCHAR(255) NOT NULL, -- 更长的密码字段以适应哈希后的结果
    role_list    VARCHAR(255),          -- 角色列表,角色Id,使用逗号分隔
    nick_name VARCHAR(400) ,
    create_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status       TINYINT   DEFAULT 1,   -- 假设1代表激活状态
    INDEX idx_account (account),
    INDEX idx_user_name (user_name)
) ENGINE =
    InnoDB
    DEFAULT
    CHARSET =
    utf8mb4
    COLLATE =
    utf8mb4_unicode_ci;

# 系统角色表设计
CREATE TABLE sys_roles
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(255)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

