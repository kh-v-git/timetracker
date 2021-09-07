DROP DATABASE IF EXISTS time_tracker_db;

CREATE DATABASE time_tracker_db;
USE time_tracker_db;

CREATE TABLE user(
                     user_id INT AUTO_INCREMENT PRIMARY KEY ,
                     email VARCHAR(255) UNIQUE NOT NULL ,
                     password VARCHAR(255) NOT NULL ,
                     role VARCHAR(255) NOT NULL,
                     first_name VARCHAR(255) NOT NULL ,
                     last_name VARCHAR(255) NOT NULL
);

CREATE TABLE activity_category (
                                   category_id INT AUTO_INCREMENT PRIMARY KEY ,
                                   category_name VARCHAR(255) UNIQUE NOT NULL,
                                   category_description TINYTEXT
);

CREATE TABLE activity (
                          activity_id INT AUTO_INCREMENT PRIMARY KEY ,
                          category_id INT,
                          FOREIGN KEY (category_id)
                              REFERENCES activity_category (category_id)
                              ON DELETE CASCADE
                              ON UPDATE CASCADE ,
                          activity_name VARCHAR(255) UNIQUE NOT NULL,
                          activity_description TINYTEXT
);
CREATE TABLE user_activity (
                               user_activity_id INT AUTO_INCREMENT PRIMARY KEY,
                               user_id INT ,
                               FOREIGN KEY (user_id)
                                   REFERENCES user (user_id)
                                   ON DELETE CASCADE
                                   ON UPDATE CASCADE ,
                               activity_id INT,
                               FOREIGN KEY (activity_id)
                                   REFERENCES activity (activity_id)
                                   ON DELETE CASCADE
                                   ON UPDATE CASCADE ,
                               status VARCHAR(255)
);
CREATE TABLE user_activity_time_log (
                                        time_log_id INT AUTO_INCREMENT PRIMARY KEY ,
                                        user_activity_id INT,
                                        FOREIGN KEY (user_activity_id)
                                            REFERENCES user_activity (user_activity_id)
                                            ON DELETE CASCADE
                                            ON UPDATE CASCADE ,
                                        activity_start_date DATE NOT NULL ,
                                        activity_time_log INT NOT NULL
);
