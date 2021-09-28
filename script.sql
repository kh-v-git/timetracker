
SET NAMES 'utf8';
SET CHARACTER SET 'utf8';

DROP DATABASE IF EXISTS time_tracker_db;

CREATE DATABASE time_tracker_db;
USE time_tracker_db;

CREATE TABLE user(
                     userId INT AUTO_INCREMENT PRIMARY KEY ,
                     userEmail VARCHAR(50) UNIQUE NOT NULL ,
                     userPassword VARCHAR(30) NOT NULL ,
                     userFirstName VARCHAR(50) NOT NULL ,
                     userLastName VARCHAR(50) NOT NULL,
                     userRole enum('user','admin') not null default 'user',
                     userStatus enum('new','active','deactivated') not null default 'new',
                     userAbout TINYTEXT
);

CREATE TABLE activity_category (
                                   categoryId INT AUTO_INCREMENT PRIMARY KEY ,
                                   categoryName VARCHAR(50) UNIQUE NOT NULL,
                                   categoryDescription TINYTEXT
);

CREATE TABLE activity (
                          activityId INT AUTO_INCREMENT PRIMARY KEY ,
                          categoryId INT,
                          FOREIGN KEY (categoryId)
                              REFERENCES activity_category (categoryId)
                              ON DELETE CASCADE
                              ON UPDATE CASCADE ,
                          activityName VARCHAR(50) UNIQUE NOT NULL,
                          activityDescription TINYTEXT
);
CREATE TABLE user_activity (
                               userActivityId INT AUTO_INCREMENT PRIMARY KEY,
                               userId INT ,
                               FOREIGN KEY (userId)
                                   REFERENCES user (userId)
                                   ON DELETE CASCADE
                                   ON UPDATE CASCADE ,
                               activityId INT,
                               FOREIGN KEY (activityId)
                                   REFERENCES activity (activityId)
                                   ON DELETE CASCADE
                                   ON UPDATE CASCADE ,
                               activityStatus enum('requested',
                                   'accepted',
                                   'rejected',
                                   'closed') not null default 'requested'
);
CREATE TABLE user_activity_time_log (
                                        timeLogId INT AUTO_INCREMENT PRIMARY KEY ,
                                        userActivityId INT,
                                        FOREIGN KEY (userActivityId)
                                            REFERENCES user_activity (userActivityId)
                                            ON DELETE CASCADE
                                            ON UPDATE CASCADE ,
                                        activityStartDate DATE NOT NULL ,
                                        activityTimeLog INT NOT NULL
);
