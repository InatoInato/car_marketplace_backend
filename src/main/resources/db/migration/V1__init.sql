CREATE TABLE cars (
    car_id BIGSERIAL PRIMARY KEY,
    make VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    year INT NOT NULL,
    color VARCHAR(255) NOT NULL,
    engine_capacity DOUBLE PRECISION NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    description TEXT NOT NULL
);
