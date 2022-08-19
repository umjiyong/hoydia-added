-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: hoydia_demo
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `diary`
--

DROP TABLE IF EXISTS `diary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diary` (
  `diary_id` varchar(255) NOT NULL,
  `button_color` varchar(255) DEFAULT NULL,
  `diary_color` varchar(255) DEFAULT NULL,
  `drawn` int DEFAULT NULL,
  `font_color` varchar(255) DEFAULT NULL,
  `font_size` int DEFAULT NULL,
  `font_style` varchar(255) DEFAULT NULL,
  `own` bit(1) NOT NULL,
  `owner_id` varchar(255) DEFAULT NULL,
  `pair_id` varchar(255) DEFAULT NULL,
  `reg_time` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`diary_id`),
  KEY `FKf0xms46ulxc36096k9gg6j9ip` (`user_id`),
  CONSTRAINT `FKf0xms46ulxc36096k9gg6j9ip` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diary`
--

LOCK TABLES `diary` WRITE;
/*!40000 ALTER TABLE `diary` DISABLE KEYS */;
/*!40000 ALTER TABLE `diary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `file` (
  `file_id` bigint NOT NULL AUTO_INCREMENT,
  `custom_file_name` varchar(255) DEFAULT NULL,
  `file_path` varchar(255) DEFAULT NULL,
  `orginal_file_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file`
--

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file` DISABLE KEYS */;
/*!40000 ALTER TABLE `file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `matching_note`
--

DROP TABLE IF EXISTS `matching_note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `matching_note` (
  `matching_id` varchar(255) NOT NULL,
  `owner_answer` varchar(255) DEFAULT NULL,
  `owner_id` varchar(255) DEFAULT NULL,
  `owner_permit` int NOT NULL,
  `owner_question` varchar(255) DEFAULT NULL,
  `pair_answer` varchar(255) DEFAULT NULL,
  `pair_id` varchar(255) DEFAULT NULL,
  `pair_permit` int NOT NULL,
  `pair_question` varchar(255) DEFAULT NULL,
  `reg_time` datetime(6) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`matching_id`),
  KEY `FKpt0f6k25y04qd742423v266mc` (`user_id`),
  CONSTRAINT `FKpt0f6k25y04qd742423v266mc` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `matching_note`
--

LOCK TABLES `matching_note` WRITE;
/*!40000 ALTER TABLE `matching_note` DISABLE KEYS */;
INSERT INTO `matching_note` VALUES ('4b668ff44c38ce7b65a6b588ddd33dcd58f93feb8cbdee93d7d53aef9e23e9a3','string','string2',0,'string','string','string2',0,'string','2022-08-16 14:07:43.716661','string2'),('eb79136bd2f6f5a111a1c44f92e2396904167b09fdae123adc0ac3a097c135fd','string','string2',0,'string','string','string2',0,'string','2022-08-16 14:07:43.691656','string2');
/*!40000 ALTER TABLE `matching_note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `note`
--

DROP TABLE IF EXISTS `note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `note` (
  `note_id` varchar(255) NOT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `reg_time` datetime(6) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`note_id`),
  KEY `FKmoddtnuw3yy6ct34xnw6u0boh` (`user_id`),
  CONSTRAINT `FKmoddtnuw3yy6ct34xnw6u0boh` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `note`
--

LOCK TABLES `note` WRITE;
/*!40000 ALTER TABLE `note` DISABLE KEYS */;
/*!40000 ALTER TABLE `note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `notice_id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `reg_time` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`notice_id`),
  KEY `FKcvf4mh5se36inrxn7xlh2brfv` (`user_id`),
  CONSTRAINT `FKcvf4mh5se36inrxn7xlh2brfv` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` VALUES (1,'string님과 매칭이 연결되었습니다.','2022-08-16 14:07:43.696662','매칭중! 매칭노트 : eb79136bd2f6f5a111a1c44f92e2396904167b09fdae123adc0ac3a097c135fd','string2'),(2,'string님과 매칭이 연결되었습니다.','2022-08-16 14:07:43.696662','매칭중! 매칭노트 : eb79136bd2f6f5a111a1c44f92e2396904167b09fdae123adc0ac3a097c135fd','string2'),(3,'string님과 매칭이 연결되었습니다.','2022-08-16 14:07:43.716661','매칭중! 매칭노트 : 4b668ff44c38ce7b65a6b588ddd33dcd58f93feb8cbdee93d7d53aef9e23e9a3','string2'),(4,'string님과 매칭이 연결되었습니다.','2022-08-16 14:07:43.716661','매칭중! 매칭노트 : 4b668ff44c38ce7b65a6b588ddd33dcd58f93feb8cbdee93d7d53aef9e23e9a3','string2');
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `page`
--

DROP TABLE IF EXISTS `page`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `page` (
  `page_id` varchar(255) NOT NULL,
  `bgm_path` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `content_font_size` varchar(255) DEFAULT NULL,
  `content_font_style` varchar(255) DEFAULT NULL,
  `img_path` varchar(255) DEFAULT NULL,
  `locationx` varchar(255) DEFAULT NULL,
  `locationy` varchar(255) DEFAULT NULL,
  `reg_time` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `title_font_size` varchar(255) DEFAULT NULL,
  `title_font_style` varchar(255) DEFAULT NULL,
  `diary_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`page_id`),
  KEY `FKqba2qhex78ywtpcpywaaqjvtt` (`diary_id`),
  CONSTRAINT `FKqba2qhex78ywtpcpywaaqjvtt` FOREIGN KEY (`diary_id`) REFERENCES `diary` (`diary_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `page`
--

LOCK TABLES `page` WRITE;
/*!40000 ALTER TABLE `page` DISABLE KEYS */;
/*!40000 ALTER TABLE `page` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sticker`
--

DROP TABLE IF EXISTS `sticker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sticker` (
  `sticker_id` varchar(255) NOT NULL,
  `path` varchar(255) DEFAULT NULL,
  `posx` varchar(255) DEFAULT NULL,
  `posy` varchar(255) DEFAULT NULL,
  `reg_time` datetime(6) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `page_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sticker_id`),
  KEY `FKf9ayu0gd54r419ae2lrkil5ih` (`page_id`),
  CONSTRAINT `FKf9ayu0gd54r419ae2lrkil5ih` FOREIGN KEY (`page_id`) REFERENCES `page` (`page_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sticker`
--

LOCK TABLES `sticker` WRITE;
/*!40000 ALTER TABLE `sticker` DISABLE KEYS */;
/*!40000 ALTER TABLE `sticker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `platform` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('string','string',NULL,'string1','GOOGLE','USER'),('string2','string',NULL,'string','GOOGLE','USER');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-19 10:27:11
