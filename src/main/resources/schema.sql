CREATE TABLE user(
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  phone_number VARCHAR(255),
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
