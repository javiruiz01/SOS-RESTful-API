CREATE TABLE IF NOT EXISTS USER  (
  idUser INT primary key,
  username VARCHAR(15),
  name VARCHAR(15),
  lastname VARCHAR(15),
  gender VARCHAR (10),
  mail VARCHAR (50),
  phone VARCHAR (20)
)engine = InnoDB;

CREATE TABLE IF NOT EXISTS POST (
  idPost INT primary key AUTO_INCREMENT,
  postBody TEXT,
  creationDate DATE,
  user INT,
  FOREIGN KEY (user) REFERENCES USER(idUser) ON DELETE CASCADE
)engine = InnoDB;

CREATE TABLE IF NOT EXISTS ISFRIEND (
  user1 INT,
  user2 INT,
  primary key(user1, user2),
  FOREIGN KEY (user1) REFERENCES USER(idUser) ON DELETE CASCADE,
  FOREIGN KEY (user2) REFERENCES USER(idUser) ON DELETE CASCADE
)engine = InnoDB;

INSERT INTO USER (idUser, username, name, lastname, gender, mail, phone)
VALUES	('1', 'jruiz', 'Javier', 'Ruiz', 'male', 'mail', '0212'),('2', 'yzamorano', 'Yerai', 'Zamorano', 'male', 'mail', '0414'),
		('3', 'dmelero', 'Daniel', 'Melero', 'male', 'mail', '1234'), ('4', 'carnaiz', 'Cristina', 'Arnaiz', 'female','mail', '12345'),
        ('5', 'aserradilla', 'Alejandro', 'Serradilla', 'male', 'mail', '2345');

INSERT INTO POST (idPost, postBody, creationDate, user) VALUES ('1', 'This is my first post', '2016-04-23', '1');
INSERT INTO POST (idPost, postBody, creationDate, user) VALUES ('2', 'This is my second post', '2016-04-24', '2');
INSERT INTO POST (idPost, postBody, creationDate, user) VALUES ('3', 'This is my third post', '2016-04-25', '1');
INSERT INTO POST (idPost, postBody, creationDate, user) VALUES ('4', 'This is my fourth post', '2016-04-25', '1');
INSERT INTO POST (idPost, postBody, creationDate, user) VALUES ('5', 'This is my fifth post', '2016-04-25', '1');
INSERT INTO POST (idPost, postBody, creationDate, user) VALUES ('6', 'This is my sixth post', '2016-04-25', '1');
INSERT INTO POST (idPost, postBody, creationDate, user) VALUES ('7', 'This is my seventh post', '2016-04-26', '1');
INSERT INTO POST (idPost, postBody, creationDate, user) VALUES ('8', 'This is my eigth post', '2016-04-27', '1');
INSERT INTO POST (idPost, postBody, creationDate, user) VALUES ('9', 'This is my nineth post', '2016-04-28', '1');
INSERT INTO POST (idPost, postBody, creationDate, user) VALUES ('10', 'This is my tenth post', '2016-04-28', '1');
