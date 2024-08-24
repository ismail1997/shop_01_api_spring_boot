-- Insert Data for Roles
INSERT INTO roles (name, description) VALUES
('Admin', 'Has full access to all system features and settings.'),
('Customer', 'Can browse and purchase products.'),
('Seller', 'Can list and manage their products.'),
('Support', 'Can handle customer queries and issues.'),
('Manager', 'Can manage products and view analytics.');

-- Insert Data for Users
INSERT INTO users (email, password, first_name, last_name, photos, enabled, address, postal_code, country, city) VALUES
('admin@example.com', 'hashedpassword1', 'John', 'Doe', 'admin_photo.jpg', TRUE, '123 Admin St', '10001', 'USA', 'New York'),
('customer@example.com', 'hashedpassword2', 'Jane', 'Smith', 'customer_photo.jpg', TRUE, '456 Customer Ave', '10002', 'USA', 'Los Angeles'),
('seller@example.com', 'hashedpassword3', 'Alice', 'Brown', 'seller_photo.jpg', TRUE, '789 Seller Blvd', '10003', 'USA', 'Chicago'),
('support@example.com', 'hashedpassword4', 'Bob', 'Davis', 'support_photo.jpg', TRUE, '101 Support Rd', '10004', 'USA', 'San Francisco'),
('manager@example.com', 'hashedpassword5', 'Carol', 'Wilson', 'manager_photo.jpg', TRUE, '202 Manager Lane', '10005', 'USA', 'Miami');

-- Insert Data for Users and Roles Relationship
INSERT INTO users_roles (user_id, role_id) VALUES
(1, 1),  -- Admin role for John Doe
(2, 2),  -- Customer role for Jane Smith
(3, 3),  -- Seller role for Alice Brown
(4, 4),  -- Support role for Bob Davis
(5, 5);  -- Manager role for Carol Wilson

-- Continue with the rest of the insertions...
-- Insert Data for Categories
INSERT INTO categories (name, alias, image, enabled, parent_id) VALUES
('Electronics', 'electronics', 'electronics.jpg', TRUE, NULL),
('Computers', 'computers', 'computers.jpg', TRUE, 1),
('Smartphones', 'smartphones', 'smartphones.jpg', TRUE, 1),
('Appliances', 'appliances', 'appliances.jpg', TRUE, NULL),
('Furniture', 'furniture', 'furniture.jpg', TRUE, NULL);

-- Insert Data for Brands
INSERT INTO brands (name, logo) VALUES
('Apple', 'apple_logo.jpg'),
('Samsung', 'samsung_logo.jpg'),
('Dell', 'dell_logo.jpg'),
('Sony', 'sony_logo.jpg'),
('LG', 'lg_logo.jpg');

-- Insert Data for Brands and Categories Relationship
INSERT INTO brands_categories (brand_id, category_id) VALUES
(1, 2),  -- Apple in Computers
(2, 3),  -- Samsung in Smartphones
(3, 2),  -- Dell in Computers
(4, 1),  -- Sony in Electronics
(5, 4);  -- LG in Appliances

-- Insert Data for Products
INSERT INTO products (name, alias, short_description, full_description, created_time, updated_time, enabled, in_stock, cost, price, discount_percent, length, width, height, weight, main_image, category_id, brand_id) VALUES
('iPhone 14 Pro', 'iphone-14-pro', 'Latest Apple iPhone with advanced features.', 'The iPhone 14 Pro features an all-new design, A16 chip, 5G capability, and more.', NOW(), NOW(), TRUE, FALSE, 800.00, 999.00, 10.0, 5.78, 2.82, 0.31, 0.4, 'iphone_14_pro.jpg', 3, 1),
('Galaxy S23 Ultra', 'galaxy-s23-ultra', 'Samsung’s flagship smartphone with top-notch performance.', 'The Galaxy S23 Ultra boasts a stunning display, powerful performance, and excellent camera.', NOW(), NOW(), TRUE, TRUE, 900.00, 1199.00, 15.0, 6.5, 3.0, 0.35, 0.5, 'galaxy_s23_ultra.jpg', 3, 2),
('Dell XPS 13', 'dell-xps-13', 'Compact and powerful laptop for professionals.', 'The Dell XPS 13 offers a stunning 4K display, excellent performance, and a sleek design.', NOW(), NOW(), TRUE, TRUE, 1000.00, 1299.00, 5.0, 12.0, 7.8, 0.6, 1.2, 'dell_xps_13.jpg', 2, 3),
('Sony WH-1000XM5', 'sony-wh-1000xm5', 'Industry-leading noise-canceling headphones.', 'The Sony WH-1000XM5 offers excellent sound quality, outstanding noise cancellation, and a comfortable fit.', NOW(), NOW(), TRUE, TRUE, 200.00, 349.00, 20.0, 7.8, 6.5, 3.0, 0.25, 'sony_wh_1000xm5.jpg', 1, 4),
('LG Refrigerator', 'lg-refrigerator', 'Energy-efficient refrigerator with modern features.', 'The LG Refrigerator features a spacious interior, smart cooling technology, and energy-saving design.', NOW(), NOW(), TRUE, TRUE, 500.00, 799.00, 10.0, 30.0, 32.0, 70.0, 80.0, 'lg_refrigerator.jpg', 4, 5);

-- Insert Data for Product Details
INSERT INTO product_details (name, value, product_id) VALUES
('Display Size', '6.1 inches', 1),  -- iPhone 14 Pro
('Battery Life', 'Up to 22 hours', 1),
('Processor', 'A16 Bionic', 1),
('Display Size', '6.8 inches', 2),  -- Galaxy S23 Ultra
('Battery Life', 'Up to 24 hours', 2),
('Processor', 'Exynos 2200', 2),
('Display Size', '13.4 inches', 3),  -- Dell XPS 13
('RAM', '16GB', 3),
('Processor', 'Intel Core i7', 3),
('Driver Unit', '40mm', 4),  -- Sony WH-1000XM5
('Battery Life', '30 hours', 4),
('Noise Cancellation', 'Yes', 4),
('Capacity', '350 liters', 5),  -- LG Refrigerator
('Energy Rating', 'A++', 5),
('Smart Cooling', 'Yes', 5);

-- Insert Data for Product Images
INSERT INTO product_images (name, product_id) VALUES
('iphone_14_pro_side.jpg', 1),
('iphone_14_pro_back.jpg', 1),
('galaxy_s23_ultra_side.jpg', 2),
('galaxy_s23_ultra_back.jpg', 2),
('dell_xps_13_open.jpg', 3),
('dell_xps_13_side.jpg', 3),
('sony_wh_1000xm5_case.jpg', 4),
('sony_wh_1000xm5_side.jpg', 4),
('lg_refrigerator_open.jpg', 5);