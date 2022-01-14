INSERT INTO users (username, password, enabled)
VALUES
    ('user', '{noop}123456', TRUE),
    ('admin', '{noop}123456', TRUE);

INSERT INTO authorities (username, authority)
VALUES
    ('user', 'ROLE_USER'),
    ('admin', 'ROLE_USER'),
    ('admin', 'ROLE_ADMIN');