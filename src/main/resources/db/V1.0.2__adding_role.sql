INSERT INTO user (first_name, last_name, email, password, username) VALUES ('liepa','gegere','liepa@gmail.com', '$2a$10$24wPfZ1.Q6o99vyKjFJZie2lYNLVn/aSwNedCLF6QhrdEREcaHbq6','lycustomer');

INSERT INTO role VALUES (4,'ROLE_LYCUSTOMER');

INSERT INTO users_roles (user_id, role_id) VALUES (5, 4);

