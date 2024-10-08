CREATE TABLE user
(user_id  bigint NOT NULL AUTO_INCREMENT,
    email    varchar(255) NOT NULL,
    gender   varchar(255) NOT NULL,
    mobile   varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    role     enum('USER','ADMIN') NOT NULL,
    name varchar(255) NOT NULL,
    PRIMARY KEY (user_id),
    UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO user
VALUES (1, 'admin1@example.com', 'Male', '9874563211', '$2a$10$wQAvKT.23CMK9qMqJWEaxuWAO2WuHboZaS8okIbMw9c58oAnTtNFe',
        'ADMIN', 'Admin User');

-- Password for above user: password123