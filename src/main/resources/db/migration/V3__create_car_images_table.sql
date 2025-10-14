CREATE TABLE car_images (
    image_id BIGSERIAL PRIMARY KEY,
    image_url VARCHAR(255) NOT NULL,
    car_id BIGINT NOT NULL,
    CONSTRAINT fk_car_images_car FOREIGN KEY (car_id)
        REFERENCES cars (car_id)
        ON DELETE CASCADE
);