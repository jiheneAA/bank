-- DROP TABLE IF EXISTS ACCOUNT;
CREATE TABLE ACCOUNT (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(50) NOT NULL,
    user_id INT NOT NULL,
    date_creation DATE NOT NULL,
    balance FLOAT,
    last_update DATE
);

INSERT INTO ACCOUNT VALUES (1, 123456789, 1, TODAY, 126.75, TODAY);

DROP TABLE IF EXISTS OPERATION;
CREATE TABLE OPERATION (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    amount FLOAT NOT NULL,
    date DATE NOT NULL,
    type VARCHAR(50) NOT NULL
);

ALTER TABLE OPERATION
    ADD FOREIGN KEY (account_id)
        REFERENCES ACCOUNT(id);

CREATE TABLE USER (
   id INT AUTO_INCREMENT PRIMARY KEY,
   first_name VARCHAR(50),
   last_name VARCHAR(50),
   date_birth DATE,
   address VARCHAR(50),
   phone_number VARCHAR(50),
   email VARCHAR(50)
);

INSERT INTO USER VALUES (1, 'Jihene', 'Abdelhedi', '13/01/1995', 'France', '0661122473', 'jihene.abdelhedi1@gmail.com');

