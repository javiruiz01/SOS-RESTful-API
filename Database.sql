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

INSERT INTO `APIRESTful`.`USER` (`idUser`, `username`, `name`, `lastname`, `gender`, `mail`, `phone`) VALUES ('1', 'jruiz', 'Javier', 'Ruiz', 'male', 'j.rcalle@alumnos.upm.es', '608911616');
INSERT INTO `APIRESTful`.`USER` (`idUser`, `username`, `name`, `lastname`, `gender`, `mail`, `phone`) VALUES ('2', 'yzamorano', 'Yerai', 'Zamorano', 'male', 'yerai.zgrana@alumnos.upm.es', '648834855');
INSERT INTO `APIRESTful`.`USER` (`idUser`, `username`, `name`, `lastname`, `gender`, `mail`, `phone`) VALUES ('3', 'mlopez', 'Miguel', 'Lopez', 'male', 'mlopez@gmail.com', '643834822');
INSERT INTO `APIRESTful`.`USER` (`idUser`, `username`, `name`, `lastname`, `gender`, `mail`, `phone`) VALUES ('4', 'lgomez', 'Laura', 'Gomez', 'female', 'lgomez@gmail.com', '654321822');
INSERT INTO `APIRESTful`.`USER` (`idUser`, `username`, `name`, `lastname`, `gender`, `mail`, `phone`) VALUES ('5', 'pperez', 'Pedro', 'Perez', 'male', 'pperez@gmail.com', '672168412');
INSERT INTO `APIRESTful`.`USER` (`idUser`, `username`, `name`, `lastname`, `gender`, `mail`, `phone`) VALUES ('6', 'ipardo', 'Irene', 'Pardo', 'female', 'ipardo@gmail.com', '698349112');
INSERT INTO `APIRESTful`.`USER` (`idUser`, `username`, `name`, `lastname`, `gender`, `mail`, `phone`) VALUES ('7', 'aketchum', 'Ash', 'Ketchum', 'male', 'aketchum@pokemon.com', '612371312');
INSERT INTO `APIRESTful`.`USER` (`idUser`, `username`, `name`, `lastname`, `gender`, `mail`, `phone`) VALUES ('8', 'jsoriano', 'Javier', 'Soriano', 'male', 'jsoriano@gmail.com', '612312412');

INSERT INTO `APIRESTful`.`POST` (`idPost`, `postBody`, `creationDate`, `user`) VALUES ('1', 'This is my first post', '2016-04-01', '1');
INSERT INTO `APIRESTful`.`POST` (`idPost`, `postBody`, `creationDate`, `user`) VALUES ('2', 'This is my second post', '2016-04-02', '2');
INSERT INTO `APIRESTful`.`POST` (`idPost`, `postBody`, `creationDate`, `user`) VALUES ('3', 'This is my third post', '2016-04-03', '3');
INSERT INTO `APIRESTful`.`POST` (`idPost`, `postBody`, `creationDate`, `user`) VALUES ('4', 'This is my fourth post', '2016-04-04', '5');
INSERT INTO `APIRESTful`.`POST` (`idPost`, `postBody`, `creationDate`, `user`) VALUES ('5', 'This is my fifth post', '2016-04-05', '6');
INSERT INTO `APIRESTful`.`POST` (`idPost`, `postBody`, `creationDate`, `user`) VALUES ('6', 'This is my sixth post', '2016-04-06', '7');
INSERT INTO `APIRESTful`.`POST` (`idPost`, `postBody`, `creationDate`, `user`) VALUES ('7', 'This is my seventh post', '2016-04-07', '8');
INSERT INTO `APIRESTful`.`POST` (`idPost`, `postBody`, `creationDate`, `user`) VALUES ('8', 'This is my eigth post', '2016-04-08', '1');
INSERT INTO `APIRESTful`.`POST` (`idPost`, `postBody`, `creationDate`, `user`) VALUES ('9 ', 'This is my nineth post ', '2016-04-09', '1');
INSERT INTO `APIRESTful`.`POST` (`idPost`, `postBody`, `creationDate`, `user`) VALUES ('10', 'This is my tenth post', '2016-04-10', '2');
INSERT INTO `APIRESTful`.`POST` (`idPost`, `postBody`, `creationDate`, `user`) VALUES ('11', 'This is my eleventh post', '2016-04-11', '3');
INSERT INTO `APIRESTful`.`POST` (`idPost`, `postBody`, `creationDate`, `user`) VALUES ('12', 'This is my twelveth post', '2016-04-12', '3');
INSERT INTO `APIRESTful`.`POST` (`idPost`, `postBody`, `creationDate`, `user`) VALUES ('13', 'This is my thirteenth post', '2016-04-13', '5');
INSERT INTO `APIRESTful`.`POST` (`idPost`, `postBody`, `creationDate`, `user`) VALUES ('14', 'This is my fourteenth post', '2016-04-14', '6');
INSERT INTO `APIRESTful`.`POST` (`idPost`, `postBody`, `creationDate`, `user`) VALUES ('15', 'This is my fifteenth post', '2016-04-15', '7');
INSERT INTO `APIRESTful`.`POST` (`idPost`, `postBody`, `creationDate`, `user`) VALUES ('16', 'This is my sixteenth post', '2016-04-16', '8');
INSERT INTO `APIRESTful`.`POST` (`idPost`, `postBody`, `creationDate`, `user`) VALUES ('17', 'This is my seventeenth post', '2016-04-17', '1');

INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('1', '3');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('3', '1');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('1', '5');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('5', '1');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('1', '7');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('7', '1');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('2', '4');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('4', '2');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('2', '6');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('6', '2');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('2', '8');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('8', '2');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('3', '5');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('5', '3');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('3', '7');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('7', '3');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('4', '6');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('6', '4');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('4', '8');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('8', '4');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('5', '7');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('7', '5');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('6', '8');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('8', '6');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('3', '8');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('8', '3');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('4', '3');
INSERT INTO `APIRESTful`.`ISFRIEND` (`user1`, `user2`) VALUES ('3', '4');
