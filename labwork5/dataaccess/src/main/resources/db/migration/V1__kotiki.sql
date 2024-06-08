CREATE TABLE Owners (
                        id INT PRIMARY KEY NOT NULL,
                        name VARCHAR(100) NOT NULL,
                        birth_date DATE NOT NULL
);

CREATE TABLE Cats (
                      id INT PRIMARY KEY NOT NULL,
                      name VARCHAR(100) NOT NULL,
                      birth_date DATE NOT NULL,
                      breed VARCHAR(100) NOT NULL,
                      color VARCHAR(100)  NOT NULL,
                      owner_id INTEGER REFERENCES Owners(id)
);

CREATE TABLE CatFriends (
                            cat1_id INT NOT NULL,
                            cat2_id INT NOT NULL,
                            PRIMARY KEY (cat1_id, cat2_id),
                            FOREIGN KEY (cat1_id) REFERENCES Cats(id),
                            FOREIGN KEY (cat2_id) REFERENCES Cats(id)
);
CREATE TABLE Users (
                       user_id INT PRIMARY KEY NOT NULL,
                       username VARCHAR(100) NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       role VARCHAR(00) NOT NULL,
                       owner_id INTEGER REFERENCES Owners(id)
)