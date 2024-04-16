CREATE TABLE if not exists Users (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50),
    user_password VARCHAR(50),
    email VARCHAR(100)
);

CREATE TABLE if not exists Wishlists(
    ID INT AUTO_INCREMENT PRIMARY KEY,
    ID INT,
    wishlist_name VARCHAR(100) NOT NULL,
    FOREIGN KEY (ID) REFERENCES Users(ID)
);

CREATE TABLE if not exists Items(
    wishlistID int,
    name VARCHAR(100) PRIMARY KEY,
    price DOUBLE,
    URL VARCHAR(255),
    FOREIGN KEY (wishlistID) REFERENCES Wishlists(ID)
);

CREATE TABLE if not exists Friends (
    userID1 INT,
    userID2 INT,
    PRIMARY KEY (userID1, userID2),
    FOREIGN KEY (userID1) REFERENCES Users(ID),
    FOREIGN KEY (userID2) REFERENCES Users(ID)
);