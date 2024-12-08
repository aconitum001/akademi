DROP DATABASE IF EXISTS `Akademi`;
CREATE DATABASE `Akademi`;
USE `Akademi`;

CREATE TABLE users (
    userId INT PRIMARY KEY,    
    userName VARCHAR(30),
    email VARCHAR(30),
    password VARCHAR(30)
);

INSERT INTO users (userId, userName, email, password) 
VALUES (1, "Skander", "skander@gmail.com", "azerty");

CREATE TABLE teacher (
    CIN VARCHAR(15) PRIMARY KEY,
    fullName VARCHAR(30) NOT NULL,
    phoneNumber VARCHAR(15),
    profession VARCHAR(30),
    CONSTRAINT phoneNumber_check CHECK (LENGTH(phoneNumber) = 8),
    CONSTRAINT CIN_check CHECK (LENGTH(CIN) = 8)
);


CREATE TABLE filiers (
	filierID INT AUTO_INCREMENT PRIMARY KEY,
    filierName VARCHAR(20)
);

INSERT INTO filiers (filierName)
VALUES ('CS'),('IRS'),('SE');

CREATE TABLE matiers (
    matierID INT AUTO_INCREMENT PRIMARY KEY,
    label VARCHAR(40) NOT NULL,
    coeff DECIMAL(5,2),
    filierID INT,
    CONSTRAINT filierID_FK FOREIGN KEY (filierID) REFERENCES filiers (filierID)
);

DROP TABLE matiers;


CREATE TABLE students (
	CIN VARCHAR(15) PRIMARY KEY,
    fullName VARCHAR(30) NOT NULL,
    classe VARCHAR(15) NOT NULL,
    filiere VARCHAR(5),
    CONSTRAINT filiere_CK CHECK (filiere IN ('CS','IRS','SE'))
);

CREATE TABLE notes (
	studentCIN varchar(15) PRIMARY KEY,
    matierID INT,
    note DECIMAL(5,2),
    CONSTRAINT studentCIN_FK FOREIGN KEY (studentCIN) REFERENCES students(CIN),
	CONSTRAINT matierID_FK FOREIGN KEY (matierID) REFERENCES matiers(matierID)
);






-- Inserting matiers for filier CS
INSERT INTO matiers (label, coeff, filierID)
VALUES 
    ('Mathématiques', 3.5, 1),
    ('Algorithmes', 4.0, 1),
    ('Programmation', 5.0, 1),
    ('Base de Données', 4.5, 1),
    ('Systèmes d\'Exploitation', 3.0, 1);

-- Inserting matiers for filier IRS
INSERT INTO matiers (label, coeff, filierID)
VALUES 
    ('Sécurité des Réseaux', 4.5, 2),
    ('Cryptographie', 4.0, 2),
    ('Virtualisation', 3.5, 2),
    ('Protocoles Réseaux', 4.0, 2),
    ('Gestion de Projet', 2.5, 2);

-- Inserting matiers for filier SE
INSERT INTO matiers (label, coeff, filierID)
VALUES 
    ('Génie Logiciel', 5.0, 3),
    ('Conception UML', 4.0, 3),
    ('Tests Logiciels', 3.5, 3),
    ('Qualité Logicielle', 3.0, 3),
    ('Gestion Agile', 4.0, 3);