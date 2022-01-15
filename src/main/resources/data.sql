INSERT INTO users (username, password, email)
VALUES
    ('user', '$2a$12$SiNlD4Bd3qOig2A6dMvN4O9BlnvfVuZRaWFQsXw45HwP8oYPgMAru', 'user@gmail.com'),
    ('admin', '$2a$12$SiNlD4Bd3qOig2A6dMvN4O9BlnvfVuZRaWFQsXw45HwP8oYPgMAru', 'admin@gmail.com');

INSERT INTO authorities (name)
VALUES
    ('ROLE_USER'),
    ('ROLE_ADMIN');

INSERT INTO user_roles(user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles(user_id, role_id) VALUES (2, 2);
