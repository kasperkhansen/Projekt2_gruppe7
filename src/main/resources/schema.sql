
CREATE TABLE if not exists Users (
    userID INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50),
    user_password VARCHAR(50),
    email VARCHAR(100)
);


CREATE TABLE if not exists Wishlists(
    wishlistID INT AUTO_INCREMENT PRIMARY KEY,
    userID INT,
    wishlist_name VARCHAR(100) NOT NULL,
    FOREIGN KEY (userID) REFERENCES users(userID)
);


CREATE TABLE if not exists Items(
    wishlistID int,
    Pname VARCHAR(100) PRIMARY KEY,
    price DOUBLE,
    URL VARCHAR(255),
    FOREIGN KEY (wishlistID) REFERENCES wishlists(wishlistID)
);