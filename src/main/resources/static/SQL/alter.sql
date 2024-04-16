
-- Step 3: Add the new foreign key in the `Wishlists` table
ALTER TABLE Wishlists
    ADD CONSTRAINT wishlists_ibfk_1
        FOREIGN KEY (ID) REFERENCES Users(ID);



