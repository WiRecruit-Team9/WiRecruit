# ----------------------------------------------------------
# SQL Script to Create Four wiRecruitDB Tables
# ----------------------------------------------------------

SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `User`;
DROP TABLE IF EXISTS `Recruit`;
DROP TABLE IF EXISTS `Group`;
DROP TABLE IF EXISTS `GroupUser`;
DROP TABLE IF EXISTS `GroupRecruit`;
DROP TABLE IF EXISTS `UserPhoto`;
DROP TABLE IF EXISTS `RecruitPhoto`;
DROP TABLE IF EXISTS `Upvote`;
DROP TABLE IF EXISTS `Event`;


/* The User table contains attributes of interest of a user. */
CREATE TABLE `User`
(
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR (32) NOT NULL,
    password VARCHAR (256) NOT NULL,
    first_name VARCHAR (32) NOT NULL,
    last_name VARCHAR (32) NOT NULL,
    title VARCHAR (255) NOT NULL,
    school VARCHAR (255) NOT NULL,
    city VARCHAR (255) NOT NULL,
    state VARCHAR (32) NOT NULL,
    zipcode INT NOT NULL,
    security_question INT NOT NULL,
    security_answer VARCHAR (255) NOT NULL,
    email VARCHAR(64) NOT NULL,      
    PRIMARY KEY (id)
);

CREATE TABLE `Group`
(
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR (255) NOT NULL,
    passcode INT NOT NULL,
    PRIMARY KEY (id)
);

/* The User table contains attributes of interest of a user. */
CREATE TABLE `Recruit`
(
    id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR (32) NOT NULL,
    last_name VARCHAR (32) NOT NULL,
    school VARCHAR (255) NOT NULL,
    address1 VARCHAR (255) NOT NULL,
    address2 VARCHAR (255),
    city VARCHAR (255) NOT NULL,
    state VARCHAR (32) NOT NULL,
    zipcode INT NOT NULL,
    year VARCHAR(255) NOT NULL,
    recruitedYear VARCHAR(255) NOT NULL,
    height int NOT NULL,
    weight int NOT NULL,
    gpa FLOAT NOT NULL,
    phone VARCHAR(15) NOT NULL,
    email VARCHAR(255) NOT NULL,
    skill_level INT NOT NULL,
    position VARCHAR (255) NOT NULL,
    secondary_position VARCHAR (255),
    commitment VARCHAR (255),
    notes text,
    PRIMARY KEY (id)
);

/* The Group User table contains attributes of the group and the user in the group. */
CREATE TABLE `GroupUser` 
(
    id INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id),
    user_id INT NOT NULL,
    group_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES `User`(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (group_id) REFERENCES `Group`(id) ON DELETE CASCADE
);

/* The Group Recruit table contains attributes of the group and the recruit in the group. */
CREATE TABLE `GroupRecruit`
(
    id INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id),
    recruit_id INT NOT NULL,
    group_id INT NOT NULL,
    FOREIGN KEY (recruit_id) REFERENCES `Recruit`(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (group_id) REFERENCES `Group`(id) ON DELETE CASCADE
);

/* The Photo table contains attributes of interest of a user's photo. */
CREATE TABLE `UserPhoto`
(
       id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
       extension ENUM('jpeg', 'jpg', 'png') NOT NULL,
       user_id INT,
       FOREIGN KEY (user_id) REFERENCES `User`(id) ON DELETE CASCADE
);

/* The Photo table contains attributes of interest of a recruit's photo. */
CREATE TABLE `RecruitPhoto`
(
       id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
       extension ENUM('jpeg', 'jpg', 'png') NOT NULL,
       recruit_id INT,
       FOREIGN KEY (recruit_id) REFERENCES `Recruit`(id) ON DELETE CASCADE
);

/* The Upvote table contains foreign keys for the user and recruit ids */
CREATE TABLE `Upvote`
(
       id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
       user_id INT,
       recruit_id INT,
       FOREIGN KEY (user_id) REFERENCES `User`(id) ON DELETE CASCADE,
       FOREIGN KEY (recruit_id) REFERENCES `Recruit`(id) ON DELETE CASCADE
);

/* The Event table contains attributes of interest of an event. */
CREATE TABLE `Event`
(
    id INT NOT NULL AUTO_INCREMENT,
    description text NOT NULL,
    type INT NOT NULL,
    user_id INT,
    recruit_id INT,
    group_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES `User`(id) ON DELETE CASCADE,
    FOREIGN KEY (recruit_id) REFERENCES `Recruit`(id) ON DELETE CASCADE,
    FOREIGN KEY (group_id) REFERENCES `Group`(id) ON DELETE CASCADE
);

/* The Comment table contains attributes of interest of an comment. */
CREATE TABLE `Comment`
(
    id INT NOT NULL AUTO_INCREMENT,
    comment_text text NOT NULL,
    user_id INT,
    recruit_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES `User`(id) ON DELETE CASCADE,
    FOREIGN KEY (recruit_id) REFERENCES `Recruit`(id) ON DELETE CASCADE
);