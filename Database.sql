CREATE TABLE IF NOT EXISTS USER  (
  idUser INT primary key,
  username VARCHAR(15),
  postNumber INT,
  name VARCHAR(15),
  lastname VARCHAR(15),
  gender VARCHAR (10),
  mail VARCHAR (50),
  phone VARCHAR (20)
)engine = InnoDB;

CREATE TABLE IF NOT EXISTS POST (
  idPost INT primary key AUTO_INCREMENT,
  postBody TEXT,
  fechaCreation DATE,
  user INT,
  FOREIGN KEY (user) REFERENCES USER(idUser) ON UPDATE CASCADE
)engine = InnoDB;

CREATE TABLE IF NOT EXISTS ISFRIEND (
  user1 INT,
  user2 INT,
  primary key(user1, user2),
  FOREIGN KEY (user1) REFERENCES USER(idUser) ON UPDATE CASCADE,
  FOREIGN KEY (user2) REFERENCES USER(idUser) ON UPDATE CASCADE
)engine = InnoDB;

INSERT INTO USER (idUser, username, postNumber, name, lastname, gender, mail, phone)
VALUES	('1', 'jruiz', '50', 'Javier', 'Ruiz', 'male', 'mail', '0212'),('2', 'yzamorano', '50', 'Yerai', 'Zamorano', 'male', 'mail', '0414');

INSERT INTO POST (idPost, postBody, fechaCreation, user) VALUES ('1', 'This is my first post', '2016-04-23', '1');
INSERT INTO POST (idPost, postBody, fechaCreation, user) VALUES ('2', 'This is my second post', '2016-04-24', '2');
