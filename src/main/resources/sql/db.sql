CREATE TABLE CATEGORIES (
 ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 CATEGORY_NAME VARCHAR (15) NOT NULL
);

CREATE TABLE PRODUCTS (
 ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 NAME VARCHAR (50) NOT NULL,
 PRICE DECIMAL,
 DESCRIPTION VARCHAR (255),
 FK_CATEGORIES BIGINT
);

CREATE TABLE USERS (
 ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 EMAIL VARCHAR (50) NOT NULL,
 PASSWORD VARCHAR (100) NOT NULL,
 TOKEN VARCHAR (100) NOT NULL,
 FIRST_NAME VARCHAR (50),
 LAST_NAME VARCHAR (50)
);


CREATE TABLE ROLE (
 NAME VARCHAR(10) PRIMARY KEY
);

CREATE TABLE USER_TO_ROLE (
 FK_USER_ID BIGINT NOT NULL,
 FK_ROLE_ID VARCHAR (10) NOT NULL,
 PRIMARY KEY (FK_USER_ID, FK_ROLE_ID),
 CONSregisterTRAINT FK_UTR_USERS FOREIGN KEY (FK_USER_ID)
   REFERENCES USERS(ID),
 CONSTRAINT FK_UTR_ROLES FOREIGN KEY (FK_ROLE_ID)
   REFERENCES ROLE(NAME)
);