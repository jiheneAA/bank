DROP TABLE IF EXISTS ACCOUNT;
CREATE TABLE ACCOUNT
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(50) NOT NULL,
    user_id        INT         NOT NULL,
    date_creation  DATE        NOT NULL,
    balance        FLOAT,
    last_update    DATE
);

INSERT INTO ACCOUNT
VALUES (1, 123456789, 1, TODAY, 126.75, TODAY);

DROP TABLE IF EXISTS OPERATION;
CREATE TABLE OPERATION
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT         NOT NULL,
    amount     FLOAT       NOT NULL,
    date       DATE        NOT NULL,
    type       VARCHAR(50) NOT NULL
);

ALTER TABLE OPERATION
    ADD FOREIGN KEY (account_id)
        REFERENCES ACCOUNT (id);

DROP TABLE IF EXISTS USER;
CREATE TABLE USER
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    birth_date DATE,
    email      VARCHAR(50),
    username   VARCHAR(50) NOT NULL,
    password   VARCHAR(50) NOT NULL,
    role       VARCHAR(50) NOT NULL,
    enable     BOOLEAN     NOT NULL
);

INSERT INTO USER
-- VALUES (1, 'Jihene', 'Abdelhedi', '1995-01-13', 'jihene.abdelhedi1@gmail.com', 'jiheneAA', 'jihene_pwd', 'USER', TRUE);
VALUES (1, 'Jihene', 'Abdelhedi', '1995-01-13', 'jihene.abdelhedi1@gmail.com', 'jiheneAA', '$2y$10$1LTz//.h7oLKEO1bbH177uk9dIhlsyvfwSuKQsb1HnZM0H165cbs.',
        'USER', TRUE);
