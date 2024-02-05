CREATE TABLE crypto (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    timestamp TIMESTAMP,
    symbol VARCHAR(255),
    price DECIMAL(10, 6)
);