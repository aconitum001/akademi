-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: akademi
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `filiers`
--

DROP TABLE IF EXISTS `filiers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `filiers` (
  `filierID` int NOT NULL AUTO_INCREMENT,
  `filierName` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`filierID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `filiers`
--

LOCK TABLES `filiers` WRITE;
/*!40000 ALTER TABLE `filiers` DISABLE KEYS */;
INSERT INTO `filiers` VALUES (1,'CS'),(2,'IRS'),(3,'SE');
/*!40000 ALTER TABLE `filiers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matiers`
--

DROP TABLE IF EXISTS `matiers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `matiers` (
  `matierID` int NOT NULL AUTO_INCREMENT,
  `label` varchar(40) NOT NULL,
  `coeff` decimal(5,2) DEFAULT NULL,
  `filierID` int DEFAULT NULL,
  PRIMARY KEY (`matierID`),
  UNIQUE KEY `label` (`label`),
  KEY `filierID_FK` (`filierID`),
  CONSTRAINT `filierID_FK` FOREIGN KEY (`filierID`) REFERENCES `filiers` (`filierID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matiers`
--

LOCK TABLES `matiers` WRITE;
/*!40000 ALTER TABLE `matiers` DISABLE KEYS */;
INSERT INTO `matiers` VALUES (1,'Mathématiques',3.50,1),(2,'Algorithmes',4.00,1),(3,'Programmation',5.00,1),(4,'Base de Données',4.50,1),(5,'Systèmes d\'Exploitation',3.00,1),(6,'Sécurité des Réseaux',4.50,2),(7,'Cryptographie',4.00,2),(8,'Virtualisation',3.50,2),(9,'Protocoles Réseaux',4.00,2),(10,'Gestion de Projet',2.50,2),(11,'Génie Logiciel',5.00,3),(12,'Conception UML',4.00,3),(13,'Tests Logiciels',3.50,3),(14,'Qualité Logicielle',3.00,3),(15,'Gestion Agile',4.00,3);
/*!40000 ALTER TABLE `matiers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `CIN` varchar(15) NOT NULL,
  `fullName` varchar(30) NOT NULL,
  `classe` varchar(15) NOT NULL,
  `filiere` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`CIN`),
  CONSTRAINT `filiere_CK` CHECK ((`filiere` in (_utf8mb4'CS',_utf8mb4'IRS',_utf8mb4'SE')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES ('14441234','Assil Mathlouthi','1ère Licence','SE');
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher` (
  `CIN` varchar(15) NOT NULL,
  `fullName` varchar(30) NOT NULL,
  `phoneNumber` varchar(15) DEFAULT NULL,
  `profession` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`CIN`),
  CONSTRAINT `CIN_check` CHECK ((length(`CIN`) = 8)),
  CONSTRAINT `phoneNumber_check` CHECK ((length(`phoneNumber`) = 8))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES ('14445320','Assil Mathlouti','97320148','Conception'),('14446780','Hidaya Tabbene','22320145','Java'),('14446790','Skander Jenhani','94415320','Flutter');
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `userId` int NOT NULL,
  `userName` varchar(30) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Skander','skander@gmail.com','azerty');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-28 19:18:22
