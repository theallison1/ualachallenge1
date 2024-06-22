INSERT INTO USER (id, username) VALUES (1, 'user1');
INSERT INTO USER (id, username) VALUES (2, 'user2');

INSERT INTO USER_FOLLOWERS (user_id, follower_id) VALUES (1, 2);
INSERT INTO USER_FOLLOWERS (user_id, follower_id) VALUES (2, 1);
