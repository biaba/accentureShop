
--
-- Dumping data for user
--
INSERT INTO user (email, password, username) VALUES
                                                 ('admin@gmail.com', '$2a$10$62AAhgEWKwHLzEs1k1dqEuwOVHIM2MgCbI.yzLbx./5m2md9StfAC','admin'),
                                                 ('baiba.skujevsk@gmail.com', '$2a$10$TZXYm3FwHbhLlGkmClsIpOnk7jQcOKe2Rt2ByHMUsTNC.u4alo5Gy','manager'),
                                                 ('aprily@inbox.lv', '$2a$10$BPFrIEJvcjMY8oV2cQHYS.hqO9g6oAXKBqWOQtcT24GBRtBIw8rCq','customer');

--
-- Dumping data for role
--

INSERT INTO role VALUES
                     (1,'ROLE_ADMIN'),(2,'ROLE_MANAGER'),(3,'ROLE_CUSTOMER');


--
-- Dumping data for table users_roles
--
INSERT INTO users_roles (user_id, role_id)
VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 2),
    (3, 3);


