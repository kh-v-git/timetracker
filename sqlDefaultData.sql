INSERT INTO time_tracker_db.activity_category (categoryName, categoryDescription)
VALUES ('Swimming', 'Swimming in the pool'),
       ('Running', 'Running in the forest'),
       ('Flying', 'Flying in the sky'),
       ('Настільні ігри', 'Настільні ігри з друзями'),
       ('Активні ігри', 'Активні ігри з друзями');

INSERT INTO time_tracker_db.activity(categoryId, activityName, activityDescription)
VALUES ((SELECT categoryId FROM activity_category WHERE categoryName = 'Swimming'), 'Swim 50m',
        'Swimming 50m no rescue'),
       ((SELECT categoryId FROM activity_category WHERE categoryName = 'Swimming'), 'Swim 100m',
        'Swimming 100m no rescue'),
       ((SELECT categoryId FROM activity_category WHERE categoryName = 'Swimming'), 'Swim 150m',
        'Swimming 150m no rescue'),
       ((SELECT categoryId FROM activity_category WHERE categoryName = 'Swimming'), 'Пливти 500м',
        'Пливти 500м без допомоги');

INSERT INTO time_tracker_db.activity(categoryId, activityName, activityDescription)
VALUES ((SELECT categoryId FROM activity_category WHERE categoryName = 'Running'), 'Run 50m', 'Running 50m no rescue'),
       ((SELECT categoryId FROM activity_category WHERE categoryName = 'Running'), 'Run 100m',
        'Running 100m no rescue'),
       ((SELECT categoryId FROM activity_category WHERE categoryName = 'Running'), 'Run 150m',
        'Running 150m no rescue'),
       ((SELECT categoryId FROM activity_category WHERE categoryName = 'Running'), 'Бігти 500м',
        'Бігти 500м без допомоги');

INSERT INTO time_tracker_db.activity(categoryId, activityName, activityDescription)
VALUES ((SELECT categoryId FROM activity_category WHERE categoryName = 'Flying'), 'Fly 50m', 'Flying 50m no rescue'),
       ((SELECT categoryId FROM activity_category WHERE categoryName = 'Flying'), 'Fly 100m', 'Flying 100m no rescue'),
       ((SELECT categoryId FROM activity_category WHERE categoryName = 'Flying'), 'Fly 150m', 'Flying 150m no rescue'),
       ((SELECT categoryId FROM activity_category WHERE categoryName = 'Flying'), 'Пролетіти 500м',
        'Пролетіти 500м без допомоги');

INSERT INTO time_tracker_db.activity(categoryId, activityName, activityDescription)
VALUES ((SELECT categoryId FROM activity_category WHERE categoryName = 'Настільні ігри'), 'Король Лев 10хв',
        'Гра для дітей'),
       ((SELECT categoryId FROM activity_category WHERE categoryName = 'Настільні ігри'), 'Каркасон 30хв',
        'Гра для дорослих і дітей'),
       ((SELECT categoryId FROM activity_category WHERE categoryName = 'Настільні ігри'), 'Середньовіччя 5год',
        'Гра для дорослих від 2х осіб'),
       ((SELECT categoryId FROM activity_category WHERE categoryName = 'Настільні ігри'), 'Колонізація Марсу 7год',
        'Гра для дорослих від 4х осіб');

INSERT INTO time_tracker_db.user(userEmail, userPassword, userFirstName, userLastName, userRole, userStatus, userAbout)
VALUES ('user@user.com', '123', 'User', 'User', 'user', 'active', 'Default test user'),
       ('root@root.com', '123', 'Root', 'Root', 'admin', 'active', 'Default admin user. Full access.'),
       ('bot@bot.com', '123', 'Bot', 'Tob', 'user', 'new', 'Test user. Can be deleted.'),
       ('tobot@bot.com', '123', 'Тарас', 'Бульба', 'user', 'deactivated', 'Бот-коза Тарас.');

