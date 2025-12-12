
CREATE TABLE IF NOT EXISTS `user` (
  id VARCHAR(50) NOT NULL,
  dob DATE,
  email VARCHAR(255),
  first_name VARCHAR(100),
  gender VARCHAR(10),
  last_name VARCHAR(100),
  password VARCHAR(255),
  role VARCHAR(50),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
