CREATE TABLE if not exists Users (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    user_password VARCHAR(50),
    email VARCHAR(100)
);

CREATE TABLE if not exists Wishlists(
    ID INT AUTO_INCREMENT PRIMARY KEY,
    user_ID INT,
    wishlist_name VARCHAR(100) NOT NULL,
    FOREIGN KEY (user_ID) REFERENCES Users(ID)
);

CREATE TABLE if not exists Items(
    name VARCHAR(100) PRIMARY KEY,
    wishlist_ID int,
    price DOUBLE,
    URL VARCHAR(255),
    FOREIGN KEY (wishlist_ID) REFERENCES Wishlists(ID)
);

CREATE TABLE if not exists Friends (
    user_ID1 INT,
    user_ID2 INT,
    PRIMARY KEY (user_ID1, user_ID2),
    FOREIGN KEY (user_ID1) REFERENCES Users(ID),
    FOREIGN KEY (user_ID2) REFERENCES Users(ID)
);