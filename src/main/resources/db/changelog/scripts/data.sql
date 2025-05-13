INSERT INTO "user" (name, date_of_birth, password) VALUES
('Иван Иванов', to_date('12.05.1990', 'dd.mm.yyyy'), 'password123'),
('Мария Петрова', to_date('23.11.1985', 'dd.mm.yyyy'), 'securepass456'),
('Алексей Смирнов', to_date('01.01.2000', 'dd.mm.yyyy'), 'qwerty789'),
('Ольга Кузнецова', to_date('15.07.1995', 'dd.mm.yyyy'), 'olgaPass2024'),
('Дмитрий Соколов', to_date('30.09.1988', 'dd.mm.yyyy'), 'dmitryStrongPass');

-- Для пользователя 1
INSERT INTO phone (number, user_id) VALUES
('79991234567', 1),
('79991234568', 1),
('79991234569', 1);

-- Для пользователя 2
INSERT INTO phone (number, user_id) VALUES
('78881234567', 2),
('78881234568', 2),
('78881234569', 2);

-- Для пользователя 3
INSERT INTO phone (number, user_id) VALUES
('77771234567', 3),
('77771234568', 3),
('77771234569', 3);

-- Для пользователя 4
INSERT INTO phone (number, user_id) VALUES
('76661234567', 4),
('76661234568', 4),
('76661234569', 4);

-- Для пользователя 5
INSERT INTO phone (number, user_id) VALUES
('75551234567', 5),
('75551234568', 5),
('75551234569', 5);

-- Для пользователя 1
INSERT INTO email (email, user_id) VALUES
('ivanov1@example.com', 1),
('ivanov2@example.com', 1),
('ivanov3@example.com', 1);

-- Для пользователя 2
INSERT INTO email (email, user_id) VALUES
('petrova1@mail.ru', 2),
('petrova2@mail.ru', 2),
('petrova3@mail.ru', 2);

-- Для пользователя 3
INSERT INTO email (email, user_id) VALUES
('smirnov1@yandex.ru', 3),
('smirnov2@yandex.ru', 3),
('smirnov3@yandex.ru', 3);

-- Для пользователя 4
INSERT INTO email (email, user_id) VALUES
('kuznetsova1@gmail.com', 4),
('kuznetsova2@gmail.com', 4),
('kuznetsova3@gmail.com', 4);

-- Для пользователя 5
INSERT INTO email (email, user_id) VALUES
('sokolov1@outlook.com', 5),
('sokolov2@outlook.com', 5),
('sokolov3@outlook.com', 5);

INSERT INTO account (user_id, balance) VALUES
(1, 10500.75),
(2, 2300.00),
(3, 987654.32),
(4, 150.50),
(5, 50000.00);