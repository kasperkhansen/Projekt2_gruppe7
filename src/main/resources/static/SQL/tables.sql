CREATE TABLE if not exists Users (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    user_password VARCHAR(50),
    email VARCHAR(100)
);

CREATE TABLE if not exists Wishlists(
    ID INT AUTO_INCREMENT PRIMARY KEY,
    user_ID INT NOT NULL,
    wishlist_name VARCHAR(100) NOT NULL,
    FOREIGN KEY (user_ID) REFERENCES Users(ID)
);

CREATE TABLE if not exists Items(
    ID INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    wishlist_ID int NOT NULL,
    price DOUBLE,
    URL VARCHAR(255),
    is_reserved BOOLEAN DEFAULT 0, -- 0 = false, 1 = true
    FOREIGN KEY (wishlist_ID) REFERENCES Wishlists(ID)
);

CREATE TABLE if not exists ReservedItems(
    item_ID INT NOT NULL,
    user_ID INT NOT NULL,
    FOREIGN KEY (item_ID) REFERENCES Items(ID) ON DELETE CASCADE,
    FOREIGN KEY (user_ID) REFERENCES Users(ID) ON DELETE CASCADE
);

CREATE TABLE if not exists Friends (
    user_ID1 INT,
    user_ID2 INT,
    PRIMARY KEY (user_ID1, user_ID2),
    FOREIGN KEY (user_ID1) REFERENCES Users(ID) ON DELETE CASCADE,
    FOREIGN KEY (user_ID2) REFERENCES Users(ID) ON DELETE CASCADE
);