INSERT INTO users (username, password, enabled)
VALUES
    ('user', '$2a$12$SiNlD4Bd3qOig2A6dMvN4O9BlnvfVuZRaWFQsXw45HwP8oYPgMAru', TRUE),
    ('admin', '$2a$12$SiNlD4Bd3qOig2A6dMvN4O9BlnvfVuZRaWFQsXw45HwP8oYPgMAru', TRUE);

INSERT INTO authorities (username, authority)
VALUES
    ('user', 'ROLE_USER'),
    ('admin', 'ROLE_USER'),
    ('admin', 'ROLE_ADMIN');