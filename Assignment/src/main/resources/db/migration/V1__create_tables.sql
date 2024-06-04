CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE users_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);

CREATE TABLE folder (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE file_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    path VARCHAR(255) NOT NULL,
    size BIGINT NOT NULL,
    upload_time TIMESTAMP NOT NULL,
    user_id BIGINT,
    folder_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (folder_id) REFERENCES folder(id)
);
