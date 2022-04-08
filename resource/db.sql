
-- DB DEFINITIONS
USE master;
GO

DROP DATABASE CHAT_APP
GO

CREATE DATABASE CHAT_APP;
GO

USE CHAT_APP;
GO

CREATE TABLE dbo.[USER] (
	id INT PRIMARY KEY IDENTITY(1, 1),
	username VARCHAR(25) UNIQUE,
	password VARCHAR(25),
	name VARCHAR(50)
);

CREATE TABLE dbo.[ROOM] (
	id INT PRIMARY KEY IDENTITY(1, 1),
	name VARCHAR(50),
	type SMALLINT
);

CREATE TABLE dbo.[FRIENDSHIP] (
	id INT PRIMARY KEY IDENTITY(1, 1),
	user_id INT,
	friend_id INT,
	room_id INT,
	add_friend_time DATETIME DEFAULT CURRENT_TIMESTAMP,
	status SMALLINT DEFAULT 1,
	CONSTRAINT fk_friendship_user_1 FOREIGN KEY (user_id) REFERENCES dbo.[USER](id),
	CONSTRAINT fk_friendship_user_2 FOREIGN KEY (friend_id) REFERENCES dbo.[USER](id),
	CONSTRAINT fk_friendship_room FOREIGN KEY (room_id) REFERENCES dbo.[ROOM](id)
);

CREATE TABLE dbo.[ROOM_USER] (
	id INT PRIMARY KEY IDENTITY(1, 1),
	room_id INT,
	user_id INT,
	CONSTRAINT fk_room_user_room FOREIGN KEY (room_id) REFERENCES dbo.[ROOM](id),
	CONSTRAINT fk_room_user_user FOREIGN KEY (user_id) REFERENCES dbo.[USER](id)
);

CREATE TABLE dbo.[MESSAGE] (
	id INT PRIMARY KEY IDENTITY(1, 1),
	room_id INT,
	user_id INT,
	content TEXT,
	created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT fk_message_user FOREIGN KEY (user_id) REFERENCES dbo.[USER](id),
	CONSTRAINT fk_message_room FOREIGN KEY (room_id) REFERENCES dbo.[ROOM](id)
);

CREATE TABLE dbo.[FRIEND_REQUEST] (
    id INT PRIMARY KEY IDENTITY(1, 1),
    sender_id INT,
    receiver_id INT,
    sent_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    status SMALLINT DEFAULT 0,
    CONSTRAINT fk_friend_request_user_1 FOREIGN KEY (sender_id) REFERENCES dbo.[USER](id),
    CONSTRAINT fk_friend_request_user_2 FOREIGN KEY (receiver_id) REFERENCES dbo.[USER](id)
);
-- DEFINITION END

-- INSERT DATA
INSERT INTO dbo.[USER]
VALUES
('admin', 'admin', 'admin'),
('user1', 'user1', 'user1'),
('user2', 'user2', 'user2');


INSERT INTO dbo.[FRIENDSHIP] (user_id, friend_id, room_id)
VALUES (1, 2, 1),(1, 3, 2),(2, 3, 3);

INSERT INTO dbo.[ROOM] VALUES
('admin_user1', 0),
('admin_user2', 0),
('user1_user2', 0);

INSERT INTO dbo.[ROOM_USER] VALUES
(1, 1),
(1, 2),
(2, 1),
(2, 3),
(3, 2),
(3, 3);

INSERT INTO dbo.[MESSAGE](room_id, user_id, content) VALUES
(1, 2, 'Hello user2'),
(1, 3, 'Hi user1'),
(1, 2, 'How are you?'),
(1, 3, 'Im OK!, and you?'),
(1, 2, 'Im good too :))');

INSERT INTO dbo.[FRIEND_REQUEST] (sender_id, receiver_id, status)
VALUES (4,5, 0);

-- INSERT DATA END


-- TEST QUERIES
SELECT  u.id, u.name, u.position 
FROM dbo.[USER] AS u INNER JOIN dbo.[FRIEND] AS f 
ON u.id = f.friend_id 
WHERE f.user_id = 1;

SELECT r.id, r.name 
FROM dbo.[ROOM] AS r INNER JOIN dbo.[ROOM_USER] AS ru 
ON r.id = ru.room_id 
WHERE ru.user_id = 3;

SELECT u.id, u.name, u.position 
FROM ((dbo.[ROOM] AS r INNER JOIN dbo.[ROOM_USER] AS ru ON r.id = ru.room_id) 
INNER JOIN dbo.[USER] AS u ON ru.user_id = u.id) 
WHERE r.id = 1;

SELECT m.id, m.user_id, m.content, m.created_time FROM dbo.[MESSAGE] AS m INNER JOIN dbo.[ROOM] AS r ON m.room_id = r.id WHERE r.id = 1;

SELECT id, name FROM dbo.[USER] WHERE LOWER(name) LIKE '%c%';

SELECT id, sender_id, receiver_id, sent_time, status FROM dbo.[FRIEND_REQUEST] WHERE sender_id = 1
