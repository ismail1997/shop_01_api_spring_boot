-- Table for roles
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(40) NOT NULL UNIQUE,
    description VARCHAR(150) NOT NULL
);

-- Table for users
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(128) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL,
    first_name VARCHAR(45) NOT NULL,
    last_name VARCHAR(54) NOT NULL,
    photos VARCHAR(64),
    enabled BOOLEAN NOT NULL,
    address VARCHAR(255),
    postal_code VARCHAR(64),
    country VARCHAR(64),
    city VARCHAR(64)
);

-- Table for the many-to-many relationship between users and roles
CREATE TABLE IF NOT EXISTS users_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Table for categories
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(128) NOT NULL UNIQUE,
    alias VARCHAR(64) NOT NULL UNIQUE,
    image VARCHAR(128) NOT NULL,
    enabled BOOLEAN NOT NULL,
    parent_id BIGINT,
    FOREIGN KEY (parent_id) REFERENCES categories(id) ON DELETE SET NULL
);

-- Table for brands
CREATE TABLE IF NOT EXISTS brands (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(45) NOT NULL UNIQUE,
    logo VARCHAR(128) NOT NULL
);

-- Table for the many-to-many relationship between brands and categories
CREATE TABLE IF NOT EXISTS brands_categories (
    brand_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (brand_id, category_id),
    FOREIGN KEY (brand_id) REFERENCES brands(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

-- Table for products
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(256) NOT NULL UNIQUE,
    alias VARCHAR(256) NOT NULL UNIQUE,
    short_description VARCHAR(512) NOT NULL,
    full_description VARCHAR(4096) NOT NULL,
    created_time TIMESTAMP,
    updated_time TIMESTAMP,
    enabled BOOLEAN NOT NULL,
    in_stock BOOLEAN NOT NULL,
    cost FLOAT NOT NULL,
    price FLOAT NOT NULL,
    discount_percent FLOAT NOT NULL,
    length FLOAT,
    width FLOAT,
    height FLOAT,
    weight FLOAT,
    main_image VARCHAR(256) NOT NULL,
    category_id BIGINT,
    brand_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL,
    FOREIGN KEY (brand_id) REFERENCES brands(id) ON DELETE SET NULL
);

-- Table for product details
CREATE TABLE IF NOT EXISTS product_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    value VARCHAR(255) NOT NULL,
    product_id BIGINT,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Table for product images
CREATE TABLE IF NOT EXISTS product_images (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    product_id BIGINT,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);
