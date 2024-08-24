-- Delete from the tables with foreign key dependencies first
DELETE FROM product_images;
DELETE FROM product_details;
DELETE FROM products;

-- Now you can safely delete from the categories table
DELETE FROM brands_categories;
DELETE FROM categories;

-- You can also delete from the brands table if needed
DELETE FROM brands;

-- Finally, clean up the other tables
DELETE FROM users_roles;

DELETE FROM roles;
DELETE FROM users;

