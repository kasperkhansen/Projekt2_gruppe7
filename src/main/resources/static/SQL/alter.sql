-- Create the new table with desired column order
CREATE TABLE if not exists ItemsNew(
                                    ID INT AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR(100),
                                    wishlist_ID int,
                                    price DOUBLE,
                                    URL VARCHAR(255),
                                    FOREIGN KEY (wishlist_ID) REFERENCES Wishlists(ID)
);

-- Copy data from old to new table
INSERT INTO items SELECT * FROM Items;

-- Drop old table
DROP TABLE Items;

-- Rename new table to old table name
RENAME TABLE items TO Items;