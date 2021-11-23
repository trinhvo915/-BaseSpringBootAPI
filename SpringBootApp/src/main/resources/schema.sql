INSERT INTO role (id, create_at,update_at, is_active,name) 
VALUES
(UUID(), CURDATE(), CURDATE(), 1, 'USER'),
(UUID(), CURDATE(), CURDATE(), 1, 'ADMIN'),
(UUID(), CURDATE(), CURDATE(), 1, 'EXPERT');