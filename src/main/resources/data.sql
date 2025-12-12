
INSERT INTO `user` (id, dob, email, first_name, gender, last_name, password, role)
VALUES ('1', '2000-01-01', 'admin', 'Admin', '-', NULL, 'admin', 'Admin')
ON DUPLICATE KEY UPDATE email=email;